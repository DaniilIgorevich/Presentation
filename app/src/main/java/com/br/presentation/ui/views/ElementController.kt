package com.br.presentation.ui.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.br.presentation.R
import com.br.presentation.events.ElementEvent
import com.br.presentation.models.ElementModel
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun ElementController(
    modifier: Modifier = Modifier,
    elementFlow: StateFlow<ElementModel?>,
    onElementEvent: (event: ElementEvent) -> Unit
) {
    val element = elementFlow.collectAsState().value ?: return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (element) {
            is ElementModel.Text -> {
                val fontColor = element.fontColorFlow.collectAsState().value
                val fontSize = element.fontSizeFlow.collectAsState().value
                val fontWeight = element.fontWeightFlow.collectAsState().value
                val fontStyle = element.fontStyleFlow.collectAsState().value
                val fontDecoration = element.fontDecorationFlow.collectAsState().value

                SettingsTextInner(
                    fontColor = fontColor,
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                    fontStyle = fontStyle,
                    fontDecoration = fontDecoration,
                    onFontColorChanged = { color ->
                        onElementEvent(
                            ElementEvent.Text.FontColorChanged(
                                color = color,
                                elementId = element.id
                            )
                        )
                    },
                    onFontSizeChanged = { size ->
                        onElementEvent(
                            ElementEvent.Text.FontSizeChanged(
                                size = size,
                                elementId = element.id
                            )
                        )
                    },
                    onFontWeightChanged = { weight ->
                        onElementEvent(
                            ElementEvent.Text.FontWeightChanged(
                                weight = weight,
                                elementId = element.id
                            )
                        )
                    },
                    onFontStyleChanged = { style ->
                        onElementEvent(
                            ElementEvent.Text.FontStyleChanged(
                                style = style,
                                elementId = element.id
                            )
                        )
                    },
                    onFontDecorationChanged = { decoration ->
                        onElementEvent(
                            ElementEvent.Text.FontDecorationChanged(
                                decoration = decoration,
                                elementId = element.id
                            )
                        )
                    }
                )
            }
            is ElementModel.Image -> {
                val imageSize = element.sizeFlow.collectAsState().value
                val imageWidth = imageSize.first
                val imageHeight = imageSize.second

                SettingsImageInner(
                    imageWidth = imageWidth,
                    imageHeight = imageHeight,
                    onSizeChanged = { width, height ->
                        onElementEvent(
                            ElementEvent.Image.SizeChanged(
                                width = width,
                                height = height,
                                elementId = element.id
                            )
                        )
                    },
                    onImageChanged = { uri ->
                        onElementEvent(
                            ElementEvent.Image.ImageChanged(
                                uri = uri,
                                elementId = element.id
                            )
                        )
                    }
                )
            }
        }
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = {
                onElementEvent(
                    ElementEvent.Remove(
                        elementId = element.id
                    )
                )
            }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_trash),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun RowScope.SettingsTextInner(
    fontColor: Color,
    fontSize: Int,
    fontWeight: FontWeight,
    fontStyle: FontStyle,
    fontDecoration: TextDecoration,
    onFontColorChanged: (color: Color) -> Unit,
    onFontSizeChanged: (size: Int) -> Unit,
    onFontWeightChanged: (weight: FontWeight) -> Unit,
    onFontStyleChanged: (style: FontStyle) -> Unit,
    onFontDecorationChanged: (decoration: TextDecoration) -> Unit
) {
    SelectDropDownMenu(
        modifier = Modifier.size(24.dp),
        items = listOf(4, 8, 12, 14, 16, 18, 20, 22, 24, 28, 32, 48, 64),
        itemSelected = fontSize,
        onItemSelected = onFontSizeChanged
    )
    FontButton(
        iconRes = R.drawable.ic_font_bold,
        checked = fontWeight == FontWeight.Bold,
        onCheckedChange = { fontBoldChange ->
            onFontWeightChanged(
                if (fontBoldChange)
                    FontWeight.Bold
                else
                    FontWeight.Normal
            )
        }
    )
    FontButton(
        iconRes = R.drawable.ic_font_italic,
        checked = fontStyle == FontStyle.Italic,
        onCheckedChange = { fontStyleChange ->
            onFontStyleChanged(
                if (fontStyleChange)
                    FontStyle.Italic
                else
                    FontStyle.Normal
            )
        }
    )
    FontButton(
        iconRes = R.drawable.ic_font_decoration,
        checked = fontDecoration == TextDecoration.Underline,
        onCheckedChange = { fontDecorationChange ->
            onFontDecorationChanged(
                if (fontDecorationChange)
                    TextDecoration.Underline
                else
                    TextDecoration.None
            )
        }
    )
    Spacer(modifier = Modifier.weight(1f))
}

@Composable
private fun FontButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit,
    @DrawableRes iconRes: Int
) {
    IconToggleButton(
        modifier = modifier.size(24.dp),
        checked = checked,
        onCheckedChange = onCheckedChange
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp),
            tint = if (checked) Color.Magenta else Color.Black,
            painter = painterResource(id = iconRes),
            contentDescription = null
        )
    }
}

@Composable
private fun RowScope.SettingsImageInner(
    imageWidth: Float,
    imageHeight: Float,
    onSizeChanged: (width: Float, height: Float) -> Unit,
    onImageChanged: (uri: Uri) -> Unit
) {
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null)
            onImageChanged(uri)
    }

    Text(text = "Ш:")
    BasicTextField(
        modifier = Modifier.width(42.dp),
        value = imageWidth.roundToInt().toString(),
        singleLine = true,
        maxLines = 1,
        onValueChange = { widthStr ->
            val width = widthStr
                .toFloatOrNull()
                ?.absoluteValue ?: 0f

            onSizeChanged(width, imageHeight)
        },
        textStyle = TextStyle(textAlign = TextAlign.Center)
    )
    Text(text = "В:")
    BasicTextField(
        modifier = Modifier.width(42.dp),
        value = imageHeight.roundToInt().toString(),
        singleLine = true,
        maxLines = 1,
        onValueChange = { heightStr ->
            val height = heightStr
                .toFloatOrNull()
                ?.absoluteValue ?: 0f

            onSizeChanged(imageWidth, height)
        },
        textStyle = TextStyle(textAlign = TextAlign.Center)
    )
    Spacer(modifier = Modifier.weight(1f))
    IconButton(
        modifier = Modifier.size(24.dp),
        onClick = { galleryLauncher.launch("image/*") }
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_pencil),
            contentDescription = null
        )
    }
}