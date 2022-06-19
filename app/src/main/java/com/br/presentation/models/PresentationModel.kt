package com.br.presentation.models

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow

class PresentationModel(name: String = "", author: String = "") {
    val url: Uri? = null
    val nameFlow: MutableStateFlow<String> = MutableStateFlow(name)
    val authorFlow: MutableStateFlow<String> = MutableStateFlow(author)
    val slideListFlow: MutableStateFlow<List<SlideModel>> = MutableStateFlow(listOf(SlideModel(0)))
}