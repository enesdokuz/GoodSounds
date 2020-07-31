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
    @PrimaryKey
    @SerializedName("id")
    val id: String
) : Serializable