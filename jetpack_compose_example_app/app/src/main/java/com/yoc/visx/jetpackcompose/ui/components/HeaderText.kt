package com.yoc.visx.jetpackcompose.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoc.visx.jetpackcompose.ui.theme.blackText

@Composable
fun HeaderText() {
    Text(
        text = "THIS IS HEADER",
        color = blackText,
        fontSize = 22.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(0.dp, 16.dp)
    )
}