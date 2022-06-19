package com.br.presentation.models

import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import kotlinx.coroutines.flow.MutableStateFlow

sealed class ElementModel(val id: Long) {
    val position: MutableStateFlow<Offset> = MutableStateFlow(Offset.Zero)
    val focus: MutableStateFlow<Boolean> = MutableStateFlow(false)

    class Text(
        id: Long,
        text: String
    ) : ElementModel(id) {
        val textFlow: MutableStateFlow<String> = MutableStateFlow(text)
        val fontColorFlow: MutableStateFlow<Color> = MutableStateFlow(Color.Black)
        val fontSizeFlow: MutableStateFlow<Int> = MutableStateFlow(12)
        val fontWeightFlow: MutableStateFlow<FontWeight> = MutableStateFlow(FontWeight.Normal)
        val fontStyleFlow: MutableStateFlow<FontStyle> = MutableStateFlow(FontStyle.Normal)
        val fontDecorationFlow: MutableStateFlow<TextDecoration> = MutableStateFlow(TextDecoration.None)
    }

    class Image(
        id: Long,
        bitmap: Bitmap
    ) : ElementModel(id) {
        val bitmapFlow: MutableStateFlow<Bitmap> = MutableStateFlow(bitmap)
        val sizeFlow: MutableStateFlow<Pair<Float, Float>> = MutableStateFlow(250f to 250f)
    }
}