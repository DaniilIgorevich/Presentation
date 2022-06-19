package com.br.presentation.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.presentation.events.ElementEvent
import com.br.presentation.events.SlideEvent
import com.br.presentation.models.ElementModel
import com.br.presentation.models.SlideModel

@Composable
fun Slide(
    modifier: Modifier = Modifier,
    slide: SlideModel,
    editableMode: Boolean = false,
    onSlideEvent: (event: SlideEvent) -> Unit,
    onElementEvent: (event: ElementEvent) -> Unit,
) {
    val size = slide.sizeFlow.collectAsState().value
    val elements = slide.elementsFlow.collectAsState().value
    val width = size.first
    val height = size.second

    Box(
        modifier = modifier
            .size(
                width = width.dp,
                height = height.dp
            )
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(2.dp),
                clip = true
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(2.dp)
            )
    ) {
        elements.forEach { element ->
            when (element) {
                is ElementModel.Text -> TextElement(
                    editable = editableMode,
                    element = element,
                    onPositionChanged = { position ->
                        onElementEvent(
                            ElementEvent.PositionChanged(
                                position = position,
                                elementId = element.id
                            )
                        )
                    },
                    onFocusChanged = { focus ->
                        if (focus)
                            onElementEvent(
                                ElementEvent.GotFocus(
                                    elementId = element.id
                                )
                            )
                    },
                    onTextChanged = { text ->
                        onElementEvent(
                            ElementEvent.Text.TextChanged(
                                text = text,
                                elementId = element.id
                            )
                        )
                    }
                )
                is ElementModel.Image -> ImageElement(
                    editable = editableMode,
                    element = element,
                    onPositionChanged = { position ->
                        onElementEvent(
                            ElementEvent.PositionChanged(
                                position = position,
                                elementId = element.id
                            )
                        )
                    },
                    onFocusChanged = { focus ->
                        if (focus)
                            onElementEvent(
                                ElementEvent.GotFocus(
                                    elementId = element.id
                                )
                            )
                    },
                    onScaleChanged = { scale ->
                        onElementEvent(
                            ElementEvent.Image.ScaleChanged(
                                scale = scale,
                                elementId = element.id
                            )
                        )
                    }
                )
            }
        }
    }
}