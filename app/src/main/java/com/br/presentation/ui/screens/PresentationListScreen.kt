package com.br.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.presentation.PresentationListViewModel
import com.br.presentation.R
import com.br.presentation.ui.views.Toolbar

@Composable
fun PresentationListScreen(
    modifier: Modifier = Modifier,
    viewModel: PresentationListViewModel,
    onCreatePresentation: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Toolbar {
            Text(
                text = "Список призентаций",
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = onCreatePresentation
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_presentation_create),
                    tint = Color.White,
                    contentDescription = "create presentation"
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .weight(1f)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {

            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                shape = CircleShape,
                onClick = { /*TODO*/ }
            ) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_folder),
                        tint = Color.White,
                        contentDescription = "open presentation"
                    )
                    Text(
                        text = "Открыть",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}