package com.br.presentation.ui.views

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.presentation.R

@Composable
fun PresentationMenu(
    modifier: Modifier = Modifier,
    onCreateTextClicked: () -> Unit,
    onCreateImageClicked: () -> Unit,
    onCreateSlideClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PresentationMenuButton(
            iconRes = R.drawable.ic_text,
            contentDescription = "add button text element",
            onClick = onCreateTextClicked
        )
        PresentationMenuButton(
            iconRes = R.drawable.ic_image,
            contentDescription = "add button image element",
            onClick = onCreateImageClicked
        )
        Spacer(modifier = Modifier.weight(1f))
        PresentationMenuButton(
            iconRes = R.drawable.ic_add,
            contentDescription = "add slide",
            onClick = onCreateSlideClicked
        )
    }
}

@Composable
private fun  PresentationMenuButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier.size(24.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription
        )
    }
}