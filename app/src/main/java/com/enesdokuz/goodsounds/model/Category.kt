package com.enesdokuz.goodsounds.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Category (
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("id")
    val id: String
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}