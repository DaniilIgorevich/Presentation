package com.br.presentation.ui.views

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.presentation.extensions.debugLog
import com.br.presentation.extensions.thenIf
import com.br.presentation.models.ElementModel
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.roundToInt

@Composable
private fun ElementWrapper(
    modifier: Modifier = Modifier,
    editable: Boolean = false,
    positionFlow: StateFlow<Offset>,
    focusFlow: StateFlow<Boolean>,
    onPositionChanged: (offset: Offset) -> Unit = {},
    onScaleChanged: (scale: Float) -> Unit = {},
    onFocusChanged: (focus: Boolean) -> Unit = {},
    content: @Composable () -> Unit
) {
    val position = positionFlow.collectAsState().value
    val focus = focusFlow.collectAsState().value

    Column(
        modifier = modifier
            .offset {
                IntOffset(
                    x = position.x.roundToInt(),
                    y = position.y.roundToInt()
                )
            }
            .thenIf(focus && editable) {
                border(
                    width = 1.dp,
                    color = Color.Blue
                )
            }
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures { _ -> onFocusChanged(true) }
            }
            .pointerInput(Unit) {
                detectTransformGestures { _, offset, zoom, _ ->
                    onFocusChanged(true)
                    onPositionChanged(offset)
                    onScaleChanged(zoom)
                }
            }
    ) {
        if (focus && editable)
            Text(
                text = "${position.x.roundToInt()} ${position.y.roundToInt()}",
                fontSize = 8.sp
            )
        content()
    }
}

@Composable
fun TextElement(
    modifier: Modifier = Modifier,
    element: ElementModel.Text,
    editable: Boolean = false,
    onPositionChanged: (position: Offset) -> Unit,
    onFocusChanged: (focus: Boolean) -> Unit,
    onTextChanged: (text: String) -> Unit
) {
    val text = element.textFlow.collectAsState().value
    val fontColor = element.fontColorFlow.collectAsState().value
    val fontSize = element.fontSizeFlow.collectAsState().value
    val fontWeight = element.fontWeightFlow.collectAsState().value
    val fontStyle = element.fontStyleFlow.collectAsState().value
    val fontDecoration = element.fontDecorationFlow.collectAsState().value

    ElementWrapper(
        modifier = modifier,
        editable = editable,
        positionFlow = element.position,
        focusFlow = element.focus,
        onPositionChanged = onPositionChanged,
        onFocusChanged = onFocusChanged
    ) {
        BasicTextField(
            value = text,
            onValueChange = onTextChanged,
            textStyle = TextStyle(
                color = fontColor,
                fontFamily = FontFamily.Serif,
                fontSize = fontSize.sp,
                fontWeight = fontWeight,
                fontStyle = fontStyle,
                textDecoration = fontDecoration
            ),
        )
    }
}

@Composable
fun ImageElement(
    modifier: Modifier = Modifier,
    editable: Boolean = false,
    element: ElementModel.Image,
    onPositionChanged: (position: Offset) -> Unit,
    onFocusChanged: (focus: Boolean) -> Unit,
    onScaleChanged: (size: Float) -> Unit
) {
    val bitmap = element.bitmapFlow.collectAsState().value
    val size = element.sizeFlow.collectAsState().value
    val width = size.first
    val height = size.second

    ElementWrapper(
        modifier = modifier
            .size(
                width = width.dp,
                height = height.dp
            ),
        editable = editable,
        positionFlow = element.position,
        focusFlow = element.focus,
        onPositionChanged = onPositionChanged,
        onFocusChanged = onFocusChanged,
        onScaleChanged = onScaleChanged
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Image element",
            modifier = Modifier.fillMaxSize()
        )
    }
}