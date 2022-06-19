package com.br.presentation.extensions

import androidx.compose.ui.Modifier

fun Modifier.thenIf(expression: Boolean, modifierBlock: Modifier.() -> Modifier): Modifier =
    if (expression)
        this.modifierBlock()
    else this

fun Modifier.thenCase(expression: Boolean, trueBlock: Modifier.() -> Modifier, falseBlock: Modifier.() -> Modifier) =
    if (expression)
        this.trueBlock()
    else
        this.falseBlock()