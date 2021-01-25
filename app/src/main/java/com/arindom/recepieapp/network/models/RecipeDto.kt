package com.arindom.recepieapp.network.models

import com.google.gson.annotations.SerializedName

data class RecipeDto(
    val pk: Int,
    val title: String,
    val publisher: String,
    @SerializedName("featured_image")
    val featuredImage: String,
    val rating: Int,
    @SerializedName("source_url")
    val sourceURL: String,
    val description: String,
    @SerializedName("cooking_instructions")
    val cookingInstructions: String? = null,
    val ingredients: List<String> = listOf(),
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("date_updated")
    val dateUpdated: String
)