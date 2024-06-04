package com.example.compose.unit6.footmark.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Insert
    suspend fun insert(post: PostEntity)

    @Query("SELECT * FROM posts ORDER BY timestamp DESC")
    fun selectAll(): Flow<List<PostEntity>>
}
