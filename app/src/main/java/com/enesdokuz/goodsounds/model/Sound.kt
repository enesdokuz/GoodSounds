package com.enesdokuz.goodsounds.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Sound(
    @SerializedName("name")
    val name: String,
    @SerializedName("source")
    val source: String,
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("isFavorite")
    var isFavorite: Boolean = false,
    @SerializedName("isPlaying")
    var isPlaying: Boolean = false,
    @SerializedName("volume")
    var volume: Float = 0.5f
) : Serializable