package com.arindom.recepieapp.network.services

import com.arindom.recepieapp.network.models.RecipeDto
import com.arindom.recepieapp.network.responses.RecipeSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipeService {
    @GET("search")
    suspend fun getRecipes(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<RecipeSearchResponse>

    @GET("get")
    suspend fun getRecipeForId(
        @Header("Authorization") token: String,
        @Query("id") id: Int,
    ): Response<RecipeDto>
}