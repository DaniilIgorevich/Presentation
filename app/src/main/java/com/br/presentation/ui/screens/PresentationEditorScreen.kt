package com.br.presentation.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.br.presentation.PresentationEditorViewModel
import com.br.presentation.R
import com.br.presentation.events.ElementEvent
import com.br.presentation.events.PresentationEvent
import com.br.presentation.events.SlideControllerVisible
import com.br.presentation.events.SlideEvent
import com.br.presentation.models.PresentationModel
import com.br.presentation.ui.views.*

@Composable
fun PresentationEditorScreen(
    modifier: Modifier = Modifier,
    viewModel: PresentationEditorViewModel,
    onExitScreen: () -> Unit
) {
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null)
            viewModel.obtainEvent(
                ElementEvent.Image.Create(uri = uri)
            )
    }
    val savePresentationLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri ->
        if (uri != null) {
            viewModel.obtainEvent(
                PresentationEvent.SavePresentation(uri)
            )
            onExitScreen()
        }
    }
    val slideControllerVisible = viewModel.slideControllerVisible.collectAsState().value
    val scroll = rememberScrollState()

    Column(modifier = modifier) {
        Toolbar {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = onExitScreen
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.ArrowBack,
                    tint = Color.White,
                    contentDescription = "button back to presentation list"
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                text = "Редактор призентации",
                color = Color.White,
                textAlign = TextAlign.Center
            )
            SlideCountToggleButton(
                modifier = Modifier.size(20.dp),
                slideListFlow = viewModel.presentation.slideListFlow,
                checked = slideControllerVisible,
                onCheckedChange = { visible ->
                    viewModel.obtainEvent(
                        SlideControllerVisible(visible)
                    )
                },
            )
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = {
                    with(viewModel.presentation) {
                        if (url != null)
                            viewModel.obtainEvent(
                                PresentationEvent.SavePresentation(url)
                            )
                        else
                            savePresentationLauncher.launch(null)
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_save),
                    tint = Color.White,
                    contentDescription = "save presentation"
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scroll)
        )
        {
            if (slideControllerVisible)
                SlideController(
                    slideListFlow = viewModel.presentation.slideListFlow,
                    onSlideEvent = viewModel::obtainEvent
                )
            SlideEditor(
                modifier = Modifier.weight(1f),
                editableSlideFlow = viewModel.editableSlide,
                onEditorEvent = viewModel::obtainEvent,
                onSlideEvent = viewModel::obtainEvent,
                onElementEvent = viewModel::obtainEvent
            )
        }
        VerticalBottomBar {
            ElementController(
                elementFlow = viewModel.controlledElement,
                onElementEvent = viewModel::obtainEvent
            )
            PresentationMenu(
                onCreateTextClicked = {
                    viewModel.obtainEvent(
                        ElementEvent.Text.Create()
                    )
                },
                onCreateImageClicked = { galleryLauncher.launch("image/*") },
                onCreateSlideClicked = {
                    viewModel.obtainEvent(
                        SlideEvent.Create
                    )
                }
            )
        }
    }
}