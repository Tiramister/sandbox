package com.example.compose.unit1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R

@Suppress("unused")
@Composable
fun Article(modifier: Modifier = Modifier) {
    val headerImage = painterResource(id = R.drawable.bg_compose_background)

    Column(modifier = modifier) {
        Image(
            painter = headerImage,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        Text(
            text = stringResource(id = R.string.article_title),
            fontSize = 24.sp,
            modifier =
                Modifier
                    .padding(16.dp)
                    .align(Alignment.Start),
        )
        Text(
            text = stringResource(R.string.article_content_1),
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(16.dp, 0.dp),
        )
        Text(
            text = stringResource(R.string.article_content_2),
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(16.dp),
        )
    }
}
