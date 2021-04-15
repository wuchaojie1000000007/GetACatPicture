package com.example.practice.model

import com.squareup.moshi.Json

data class CatBreedData(
    val name: String,
    val temperament: String
)

data class ImageResultData(
    @field:Json(name = "url") val imageUrl: String,
    val breed: List<CatBreedData>
)