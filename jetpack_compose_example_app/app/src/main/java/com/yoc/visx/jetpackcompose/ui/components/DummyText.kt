package com.yoc.visx.jetpackcompose.ui.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoc.visx.jetpackcompose.R
import com.yoc.visx.jetpackcompose.ui.theme.blackText

@Composable
fun DummyText(context: Context) {
    Text(
        text = context.getText(R.string.dummy_text).toString(),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        color = blackText,
        fontSize = 12.sp,
        textAlign = TextAlign.Start
    )
}