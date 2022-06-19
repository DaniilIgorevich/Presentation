package com.br.presentation.ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.presentation.models.SlideModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SlideCountToggleButton(
    modifier: Modifier = Modifier,
    slideListFlow: StateFlow<List<SlideModel>>,
    checked: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit
) {
    val slideCount = slideListFlow.collectAsState().value.size


    Box(
        modifier = modifier
            .clickable { onCheckedChange(!checked) }
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = slideCount.toString(),
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}