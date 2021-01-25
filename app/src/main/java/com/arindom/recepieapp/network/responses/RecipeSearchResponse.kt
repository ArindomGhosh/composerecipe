package com.arindom.recepieapp.network.responses

import com.arindom.recepieapp.network.models.RecipeDto
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val recipes: List<RecipeDto>
)