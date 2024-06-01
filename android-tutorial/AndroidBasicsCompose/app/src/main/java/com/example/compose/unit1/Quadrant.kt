package com.example.compose.unit1

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose.R

@Suppress("unused")
@Composable
fun Quadrant() {
    Column {
        Row(modifier = Modifier.weight(1f)) {
            ComposablePart(
                title = stringResource(id = R.string.quadrant_text_title),
                description = stringResource(id = R.string.quadrant_text_description),
                bgColor = colorResource(id = R.color.quadrant_text),
                modifier = Modifier.weight(1f),
            )
            ComposablePart(
                title = stringResource(id = R.string.quadrant_image_title),
                description = stringResource(id = R.string.quadrant_image_description),
                bgColor = colorResource(id = R.color.quadrant_image),
                modifier = Modifier.weight(1f),
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            ComposablePart(
                title = stringResource(id = R.string.quadrant_row_title),
                description = stringResource(id = R.string.quadrant_row_description),
                bgColor = colorResource(id = R.color.quadrant_row),
                modifier = Modifier.weight(1f),
            )
            ComposablePart(
                title = stringResource(id = R.string.quadrant_column_title),
                description = stringResource(id = R.string.quadrant_column_description),
                bgColor = colorResource(id = R.color.quadrant_column),
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
fun ComposablePart(
    title: String,
    description: String,
    bgColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .fillMaxSize()
                .background(color = bgColor)
                .padding(16.dp),
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
        )
        Text(
            text = description,
            textAlign = TextAlign.Justify,
        )
    }
}
