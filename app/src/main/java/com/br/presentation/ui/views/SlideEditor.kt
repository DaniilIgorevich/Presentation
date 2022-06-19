package com.br.presentation.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import com.br.presentation.events.ElementEvent
import com.br.presentation.events.SlideEditorEvent
import com.br.presentation.events.Event
import com.br.presentation.events.SlideEvent
import com.br.presentation.models.SlideModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SlideEditor(
    modifier: Modifier = Modifier,
    editableSlideFlow: StateFlow<SlideModel>,
    onEditorEvent: (event: SlideEditorEvent) -> Unit,
    onSlideEvent: (event: SlideEvent) -> Unit,
    onElementEvent: (event: ElementEvent) -> Unit,
) {
    val editableSlide = editableSlideFlow.collectAsState().value
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(235, 235, 235))
            .pointerInput(Unit) {
                detectTapGestures {
                    onEditorEvent(
                        SlideEditorEvent.RemoveFocusFromAllElements
                    )
                    focusManager.clearFocus()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Slide(
            slide = editableSlide,
            editableMode = true,
            onSlideEvent = onSlideEvent,
            onElementEvent = onElementEvent
        )
    }
}