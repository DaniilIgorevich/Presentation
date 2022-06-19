package com.br.presentation.models

import android.graphics.Bitmap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SlideModel(
    val position: Int,
    width: Float = 350f,
    height: Float = 262.5f,
) {
    val sizeFlow: MutableStateFlow<Pair<Float, Float>> = MutableStateFlow(width to height)
    val elementsFlow: MutableStateFlow<List<ElementModel>> = MutableStateFlow(emptyList())

    private fun generateId(): Long {
        val elements = elementsFlow.value
        val ids = elements.map { element -> element.id }

        while(true) {
            val generatedId = (Long.MIN_VALUE..Long.MAX_VALUE).random()

            if (generatedId !in ids)
                return generatedId
        }
    }

    fun createTextElement(text: String): Unit =
        elementsFlow.update { elementHashMap ->
            elementHashMap + ElementModel.Text(id = generateId(), text = text)
        }

    fun createImageElement(bitmap: Bitmap): Unit =
        elementsFlow.update { elementHashMap ->
            elementHashMap + ElementModel.Image(id = generateId(), bitmap = bitmap)
        }

    inline fun <reified Element: ElementModel>getElementAtId(id: Long): Element =
        elementsFlow.value
            .find { element -> element.id == id }
            ?.run { this as Element }
            ?: throw ClassCastException("Element with id[$id] not found")
}