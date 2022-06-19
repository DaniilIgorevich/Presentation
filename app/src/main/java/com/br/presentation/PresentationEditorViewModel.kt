package com.br.presentation

import android.app.Application
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.br.presentation.events.*
import com.br.presentation.models.ElementModel
import com.br.presentation.models.PresentationModel
import com.br.presentation.models.SlideModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.annotations.NotNull
import java.io.OutputStream

class PresentationEditorViewModel(
    @NotNull application: Application,
    @NotNull val presentation: PresentationModel
) : AndroidViewModel(application) {
    class Factory(
        @NotNull private val application: Application,
        @NotNull private val presentation: PresentationModel =
            PresentationModel(
                name = "Новая презентация",
                author = "Неизвестно"
            )
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            modelClass
                .getConstructor(Application::class.java, PresentationModel::class.java)
                .newInstance(application, presentation)
    }

    private val _slideControllerVisible = MutableStateFlow(false)
    val slideControllerVisible = _slideControllerVisible.asStateFlow()

    private val _editableSlide: MutableStateFlow<SlideModel> = MutableStateFlow(presentation.slideListFlow.value.first())
    val editableSlide = _editableSlide.asStateFlow()

    private val _controlledElement: MutableStateFlow<ElementModel?> = MutableStateFlow(null)
    val controlledElement = _controlledElement.asStateFlow()

    private fun focusElement(element: ElementModel? = null) {
        _editableSlide.value
            .elementsFlow.value
            .forEach { iterableElement ->
                iterableElement.focus.update { iterableElement == element }
            }
    }

    private fun pickPictureFromGallery(uri: Uri): Bitmap {
        val resolver = getApplication<Application>().contentResolver

        return if (Build.VERSION.SDK_INT < 29)
            MediaStore.Images.Media
                .getBitmap(resolver, uri)
        else
            ImageDecoder
                .createSource(resolver, uri)
                .run(ImageDecoder::decodeBitmap)
    }

    fun obtainEvent(event: Event) {
        when (event) {
            is PresentationEvent.SavePresentation -> reduce(event)

            is SlideControllerVisible -> reduce(event)

            is SlideEvent.Create -> reduce(event)
            is SlideEvent.SelectSlide -> reduce(event)

            is ElementEvent.Remove -> reduce(event)
            is ElementEvent.PositionChanged -> reduce(event)
            is ElementEvent.GotFocus -> reduce(event)

            is ElementEvent.Text.Create -> reduce(event)
            is ElementEvent.Text.TextChanged -> reduce(event)
            is ElementEvent.Text.FontColorChanged -> reduce(event)
            is ElementEvent.Text.FontSizeChanged -> reduce(event)
            is ElementEvent.Text.FontWeightChanged -> reduce(event)
            is ElementEvent.Text.FontStyleChanged -> reduce(event)
            is ElementEvent.Text.FontDecorationChanged -> reduce(event)

            is ElementEvent.Image.Create -> reduce(event)
            is ElementEvent.Image.ImageChanged -> reduce(event)
            is ElementEvent.Image.ScaleChanged -> reduce(event)
            is ElementEvent.Image.SizeChanged -> reduce(event)

            is SlideEditorEvent.RemoveFocusFromAllElements -> reduce(event)
        }
    }

    private fun reduce(event: PresentationEvent.SavePresentation) {


//        val uri = event.url
//        val resolver = getApplication<Application>().contentResolver
//
//        val values = ContentValues()
//        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "menuCategory") //file name
//        values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain") //file extension, will automatically add to file
//        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/Kamen Rider Decade/") //end "/" is not mandatory
//        val uri: Uri = resolver.insert(MediaStore.Files.getContentUri("external"), values) //important!
//        val outputStream: OutputStream = resolver.openOutputStream(uri)
//        outputStream.write("This is menu category data.".toByteArray())
//        outputStream.close()

    }

    private fun reduce(event: SlideControllerVisible) {
        val visible = event.visible

        _slideControllerVisible.update { visible }
    }

    private fun reduce(event: SlideEvent.Create) {
        presentation.slideListFlow.update { slideList ->
            slideList + SlideModel(slideList.size)
        }
    }

    private fun reduce(event: SlideEvent.SelectSlide) {
        val position = event.position

        focusElement(null)
        _controlledElement.update { null }
        _editableSlide.update { presentation.slideListFlow.value[position] }
    }

    private fun reduce(event: ElementEvent.Remove) {
        val removeId = event.elementId

        focusElement(null)
        _controlledElement.update { null }

        _editableSlide.value.elementsFlow.update { elements ->
            elements.filter { element -> element.id != removeId }
        }
    }

    private fun reduce(event: ElementEvent.PositionChanged) {
        val offset = event.position
        val elementId = event.elementId

        _editableSlide.value.getElementAtId<ElementModel>(elementId)
            .position.update { position ->
                position.copy(
                    x = position.x + offset.x,
                    y = position.y + offset.y,
                )
            }
    }

    private fun reduce(event: ElementEvent.GotFocus) {
        val elementId = event.elementId
        val element = _editableSlide.value.getElementAtId<ElementModel>(elementId)

        focusElement(element)
        _controlledElement.update { element }
    }

    private fun reduce(event: ElementEvent.Text.Create) {
        val text = event.text

        _editableSlide.value.createTextElement(text)
    }

    private fun reduce(event: ElementEvent.Text.TextChanged) {
        val text = event.text
        val elementId = event.elementId

        _editableSlide.value.getElementAtId<ElementModel.Text>(elementId)
            .textFlow.update { text }
    }

    private fun reduce(event: ElementEvent.Text.FontColorChanged) {
        val color = event.color
        val elementId = event.elementId

        _editableSlide.value.getElementAtId<ElementModel.Text>(elementId)
            .fontColorFlow.update { color }
    }

    private fun reduce(event: ElementEvent.Text.FontSizeChanged) {
        val size = event.size
        val elementId = event.elementId

        _editableSlide.value.getElementAtId<ElementModel.Text>(elementId)
            .fontSizeFlow.update { size }
    }

    private fun reduce(event: ElementEvent.Text.FontWeightChanged) {
        val weight = event.weight
        val elementId = event.elementId

        _editableSlide.value.getElementAtId<ElementModel.Text>(elementId)
            .fontWeightFlow.update { weight }
    }

    private fun reduce(event: ElementEvent.Text.FontStyleChanged) {
        val style = event.style
        val elementId = event.elementId

        _editableSlide.value.getElementAtId<ElementModel.Text>(elementId)
            .fontStyleFlow.update { style }
    }

    private fun reduce(event: ElementEvent.Text.FontDecorationChanged) {
        val decoration = event.decoration
        val elementId = event.elementId

        _editableSlide.value.getElementAtId<ElementModel.Text>(elementId)
            .fontDecorationFlow.update { decoration }
    }

    private fun reduce(event: ElementEvent.Image.Create) {
        val pictureUri = event.uri
        val bitmap = pickPictureFromGallery(pictureUri)

        _editableSlide.value.createImageElement(bitmap)
    }

    private fun reduce(event: ElementEvent.Image.ImageChanged) {
        val pictureUri = event.uri
        val elementId = event.elementId
        val bitmap = pickPictureFromGallery(pictureUri)

        _editableSlide.value.getElementAtId<ElementModel.Image>(elementId)
            .bitmapFlow.update { bitmap }
    }

    private fun reduce(event: ElementEvent.Image.ScaleChanged) {
        val slideSize = _editableSlide.value.sizeFlow.value
        val slideWeight = slideSize.first
        val slideHeight = slideSize.second
        val scale = event.scale
        val elementId = event.elementId

        _editableSlide.value.getElementAtId<ElementModel.Image>(elementId)
            .sizeFlow.update { size ->
                val elementWidth = size.first * scale
                val elementHeight = size.second * scale
                val width = if (elementWidth > slideWeight) slideWeight else elementWidth
                val height = if (elementHeight > slideHeight) slideHeight else elementHeight

                width to height
            }
    }

    private fun reduce(event: ElementEvent.Image.SizeChanged) {
        val width = event.width
        val height = event.height
        val elementId = event.elementId

        _editableSlide.value.getElementAtId<ElementModel.Image>(elementId)
            .sizeFlow.update { width to height }
    }

    private fun reduce(event: SlideEditorEvent) {
        focusElement(null)
        _controlledElement.update { null }
    }
}