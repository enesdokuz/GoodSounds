package com.enesdokuz.goodsounds.repository.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enesdokuz.goodsounds.model.Category
import com.enesdokuz.goodsounds.model.Sound

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSounds(vararg sounds: Sound) : List<Long>

    @Query("SELECT * FROM Sound")
    suspend fun getSounds(): List<Sound>

    @Query("DELETE FROM Sound")
    suspend fun deleteSounds()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(vararg category: Category) : List<Long>

    @Query("SELECT * FROM Category")
    suspend fun getCategories(): List<Category>

    @Query("DELETE FROM Category")
    suspend fun deleteCategories()

    @Query("UPDATE Sound SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateSound(id: String, isFavorite: Boolean)
}