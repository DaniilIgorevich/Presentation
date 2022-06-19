package com.br.presentation.events

sealed interface SlideEvent : Event {
    object Create : SlideEvent
    data class Remove(val position: Int) : SlideEvent
    data class SelectSlide(val position: Int) : SlideEvent
}