package com.enesdokuz.goodsounds.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enesdokuz.goodsounds.model.Category
import com.enesdokuz.goodsounds.model.Sound

@Database(entities = [Category::class, Sound::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {

    abstract fun dao(): Dao

    companion object {

        @Volatile
        private var instance: MyDatabase? = null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, MyDatabase::class.java, "Database"
        ).build()
    }
}