package com.br.presentation.events

sealed interface SlideEditorEvent : Event {
    object RemoveFocusFromAllElements : SlideEditorEvent
}