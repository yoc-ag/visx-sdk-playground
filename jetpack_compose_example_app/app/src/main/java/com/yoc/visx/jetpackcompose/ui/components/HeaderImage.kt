package com.yoc.visx.jetpackcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.yoc.visx.jetpackcompose.R

@Composable
fun HeaderImage() {
    Image(
        painter = painterResource(R.drawable.teaser_img_article),
        contentDescription = "Header image",
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth
    )
}