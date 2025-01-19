package com.example.compose.unit1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R

@Suppress("unused")
@Composable
fun Task() {
    val checkCircleImage = painterResource(id = R.drawable.ic_task_completed)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = checkCircleImage,
            contentDescription = "completed",
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            text =
                stringResource(
                    id = R.string.task_completed,
                ),
            fontWeight = FontWeight.Bold,
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 24.dp, 0.dp, 8.dp),
        )
        Text(
            text = stringResource(id = R.string.task_praise),
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}
