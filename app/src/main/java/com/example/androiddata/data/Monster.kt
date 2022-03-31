package com.example.androiddata.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androiddata.utils.IMAGE_URL
import com.squareup.moshi.Json
@Entity(tableName = "monsters")
data class Monster (
    @PrimaryKey(autoGenerate = true)
    val monsterId: Int,
    @Json(name = "monsterName") val name: String,
    val imageFile: String,
    val caption: String,
    val description: String,
    val price: Double,
    val scariness: Int
) {
    val imageURL: String
        get() = "$IMAGE_URL/$imageFile.webp"
    val thumbnailURL: String
        get() = "$IMAGE_URL/${imageFile}_tn.webp"
}