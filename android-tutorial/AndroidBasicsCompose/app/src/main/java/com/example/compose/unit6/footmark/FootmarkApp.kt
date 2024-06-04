package com.example.compose.unit6.footmark

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.unit6.footmark.post.PostEntity
import com.example.compose.unit6.footmark.post.PostRepository
import com.example.compose.util.toDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FootmarkApp(postRepository: PostRepository) {
    // DB 挿入時に recomposition が走るようにする、ダミーの state
    // TODO ちゃんとした方法で実装する
    var dbState by remember {
        mutableIntStateOf(0)
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FootMark") },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(20.dp),
        ) {
            PostForm(
                postMessage = {
                    coroutineScope.launch {
                        postRepository.insert(it)
                        dbState++
                    }
                },
            )

            // どこかで dbState を使わないと recomposition が走らない
            // TODO Spacer に戻す
            Text(
                text = dbState.toString(),
                modifier =
                    Modifier
                        .size(20.dp)
                        .alpha(0f),
            )

            PostFeed(
                coroutineScope = coroutineScope,
                listState = listState,
                posts =
                    runBlocking {
                        postRepository.selectAll().first()
                    },
            )
        }
    }
}

@Composable
fun PostForm(postMessage: (String) -> Unit) {
    var formText by remember { mutableStateOf("") }

    Column(modifier = Modifier.wrapContentSize()) {
        TextField(
            value = formText,
            onValueChange = { formText = it },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
        )
        Button(onClick = {
            postMessage(formText)
            formText = ""
        }) {
            Text(text = "Post!")
        }
    }
}

@Composable
fun PostFeed(
    coroutineScope: CoroutineScope,
    listState: LazyListState,
    posts: List<PostEntity>,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = listState,
    ) {
        coroutineScope.launch {
            listState.animateScrollToItem(index = 0)
        }

        items(
            count = posts.size,
            key = { posts[it].id },
        ) { index ->
            val post = posts[index]
            Card(
                colors =
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                border = BorderStroke(1.dp, Color.Gray),
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                ) {
                    Text(text = post.timestamp.toDateTime(), fontSize = 12.sp, color = Color.Gray)
                    Text(text = post.content, fontSize = 18.sp)
                }
            }
        }
    }
}
