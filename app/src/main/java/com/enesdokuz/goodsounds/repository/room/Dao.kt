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

    @Query("SELECT * FROM Sound WHERE categoryId = :categoryId")
    suspend fun getSounds(categoryId: String): List<Sound>

    @Query("DELETE FROM Sound")
    suspend fun deleteSounds()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(vararg category: Category) : List<Long>

    @Query("SELECT * FROM Category")
    suspend fun getCategories(): List<Category>

    @Query("DELETE FROM Category")
    suspend fun deleteCategories()

    @Query("UPDATE Sound SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateSoundFav(id: String, isFavorite: Boolean)

    @Query("UPDATE Sound SET volume = :volume WHERE id = :id")
    suspend fun updateSoundVolume(id: String, volume: Float)

    @Query("SELECT * FROM Sound WHERE isFavorite = 1")
    suspend fun getMyFavorites(): List<Sound>
}