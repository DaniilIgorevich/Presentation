package com.br.presentation.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun <T>SelectDropDownMenu(
    modifier: Modifier = Modifier,
    itemSelected: T,
    items: List<T>,
    onItemSelected: (item: T) -> Unit
) {
    var text by remember { mutableStateOf(itemSelected.toString()) }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.clickable { expanded = true },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 18.sp
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {}
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        text = item.toString()
                        expanded = false
                        onItemSelected(item)
                    }
                ) {
                    Text(text = item.toString())
                }
            }
        }
    }
}