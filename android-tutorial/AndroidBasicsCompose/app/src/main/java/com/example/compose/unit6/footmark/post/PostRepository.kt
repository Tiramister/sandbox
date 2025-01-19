package com.example.compose.unit6.footmark.post

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun insert(content: String)

    fun selectAll(): Flow<List<PostEntity>>
}

class PostRepositoryImpl(context: Context) : PostRepository {
    private val postDao: PostDao = PostDatabase.getDatabase(context).postDao()

    override suspend fun insert(content: String) {
        postDao.insert(
            PostEntity(
                content = content,
                timestamp = System.currentTimeMillis(),
            ),
        )
    }

    override fun selectAll() = postDao.selectAll()
}
