package com.yoc.visx.jetpackcompose.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(text: String, func: () -> Unit) {
    androidx.compose.material.Button(onClick = {
        func.invoke()
    }) {
        TextOnButton(text)
    }
}

@Composable
fun TextOnButton(text: String) {
    Text(
        text = text,
        Modifier.width(325.dp),
        textAlign = TextAlign.Center
    )
}

