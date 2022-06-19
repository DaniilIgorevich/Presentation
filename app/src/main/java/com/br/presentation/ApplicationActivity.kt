package com.br.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.br.presentation.ui.screens.PresentationEditorScreen
import com.br.presentation.ui.screens.PresentationListScreen
import com.br.presentation.ui.theme.PresentationTheme

class ApplicationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PresentationTheme {
                Screen(
                    startDestination = Navigation.PresentationList
                )
            }
        }
    }
}

@Composable
private fun ComponentActivity.Screen(startDestination: Navigation) {
    val navController = rememberNavController()
    val screenModifier = Modifier.fillMaxSize()

    NavHost(
        navController = navController,
        startDestination = startDestination.name
    ) {
        composable(Navigation.PresentationList.name) {
            PresentationListScreen(
                viewModel = viewModel(
                    factory = ViewModelProvider.AndroidViewModelFactory(application)
                ),
                onCreatePresentation = {
                    navController.navigate(
                        Navigation.PresentationEditorScreen.name
                    )
                }
            )
        }
        composable(Navigation.PresentationEditorScreen.name) {
            PresentationEditorScreen(
                modifier = screenModifier,
                viewModel = viewModel(
                    factory = PresentationEditorViewModel.Factory(
                        application = application
                    )
                ),
                onExitScreen = { navController.navigate(Navigation.PresentationList.name) }
            )
        }
    }
}