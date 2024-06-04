package com.example.compose.unit6.footmark.post

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class PostDatabase : RoomDatabase() {
    // Room が自動で生成してくれる
    abstract fun postDao(): PostDao

    // Database のシングルトンオブジェクト
    companion object {
        // 読み書きが atomic になり、更新が他のスレッドに即時反映される
        @Volatile
        private var instance: PostDatabase? = null

        fun getDatabase(context: Context): PostDatabase =
            instance ?: synchronized(this) {
                // 競合を防ぐために、synchronized ブロック内で Database を作る
                Room.databaseBuilder(context, PostDatabase::class.java, "post_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
    }
}
