package com.br.presentation.events

sealed interface MenuEvent : Event {
    sealed interface Create {
        val slideIndex: Int

        data class TextElement(override val slideIndex: Int) : MenuEvent, Create
        data class ImageElement(override val slideIndex: Int) : MenuEvent, Create
    }
}
