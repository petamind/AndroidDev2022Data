package com.example.androiddata.data

import com.example.androiddata.utils.IMAGE_URL
import com.squareup.moshi.Json

data class Monster (
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