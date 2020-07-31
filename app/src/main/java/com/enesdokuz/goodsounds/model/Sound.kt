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
    @SerializedName("id")
    val id: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("isFavorite")
    val isFavorite: Boolean
) : Serializable{

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}