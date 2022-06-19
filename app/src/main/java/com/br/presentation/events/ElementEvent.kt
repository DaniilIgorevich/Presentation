package com.br.presentation.events

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.br.presentation.models.ElementModel

sealed interface ElementEvent : Event {
    val elementId: Long

    data class Remove(override val elementId: Long) : ElementEvent
    data class PositionChanged(val position: Offset, override var elementId: Long) : ElementEvent
    data class GotFocus(override var elementId: Long) : ElementEvent

    sealed interface Text {
        data class Create(val text: String = "Текст", override var elementId: Long = -1) : ElementEvent
        data class TextChanged(val text: String, override val elementId: Long) : ElementEvent
        data class FontColorChanged(val color: Color, override val elementId: Long) : ElementEvent
        data class FontSizeChanged(val size: Int, override val elementId: Long) : ElementEvent
        data class FontWeightChanged(val weight: FontWeight, override val elementId: Long) : ElementEvent
        data class FontStyleChanged(val style: FontStyle, override val elementId: Long) : ElementEvent
        data class FontDecorationChanged(val decoration: TextDecoration, override val elementId: Long) : ElementEvent
    }

    sealed interface Image {
        data class Create(val uri: Uri, override var elementId: Long = -1) : ElementEvent
        data class ImageChanged(val uri: Uri, override val elementId: Long) : ElementEvent
        data class ScaleChanged(val scale: Float, override val elementId: Long) : ElementEvent
        data class SizeChanged(val width: Float, val height: Float, override val elementId: Long) : ElementEvent
    }
}
