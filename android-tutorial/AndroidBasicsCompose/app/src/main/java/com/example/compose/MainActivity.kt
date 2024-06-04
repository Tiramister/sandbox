package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.compose.ui.theme.AndroidBasicsComposeTheme
import com.example.compose.unit6.footmark.FootmarkApp
import com.example.compose.unit6.footmark.post.PostRepository
import com.example.compose.unit6.footmark.post.PostRepositoryImpl

class MainActivity : ComponentActivity() {
    private lateinit var postRepository: PostRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        postRepository = PostRepositoryImpl(applicationContext)

        setContent {
            AndroidBasicsComposeTheme {
                FootmarkApp(postRepository = postRepository)
            }
        }
    }
}
