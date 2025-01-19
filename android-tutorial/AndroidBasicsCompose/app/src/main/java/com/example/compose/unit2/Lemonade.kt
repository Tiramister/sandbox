package com.example.compose.unit2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.unit2.lemonade.State
import com.example.compose.unit2.lemonade.TreeState

@Suppress("unused")
@Composable
fun LemonadeApp() {
    var state by remember {
        mutableStateOf<State>(TreeState())
    }

    Column(
        modifier = Modifier.fillMaxSize().wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { state = state.next() },
            shape = RoundedCornerShape(20.dp),
        ) {
            Image(
                painter = painterResource(id = state.imageId()),
                contentDescription = stringResource(id = state.descriptionId()),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = state.textId()), fontSize = 18.sp)
    }
}
