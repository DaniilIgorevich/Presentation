package com.br.presentation.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.PointerIconDefaults.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.presentation.events.SlideEvent
import com.br.presentation.models.SlideModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun SlideController(
    modifier: Modifier = Modifier,
    slideListFlow: StateFlow<List<SlideModel>>,
    onSlideEvent: (event: SlideEvent) -> Unit
) {
    val slides = slideListFlow.collectAsState().value

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(slides) { index, slide ->
            Box {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Слайд ${index + 1}",
                    fontSize = 12.sp
                )
                Slide(
                    modifier = Modifier
                        .scale(0.75f)
                        .clickable {
                           onSlideEvent(
                                SlideEvent.SelectSlide(slide.position)
                           )
                        }
                    ,
                    slide = slide,
                    onSlideEvent = onSlideEvent,
                    onElementEvent = {}
                )
            }
        }
    }
}