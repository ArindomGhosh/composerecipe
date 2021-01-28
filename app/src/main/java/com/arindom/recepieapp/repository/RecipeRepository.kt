package com.arindom.recepieapp.repository

import com.arindom.recepieapp.domain.Result
import com.arindom.recepieapp.domain.exceptions.NoResponseException
import com.arindom.recepieapp.domain.models.Recipe
import retrofit2.Response

interface RecipeRepository {
    suspend fun getRecipes(token: String, page: Int, query: String): Result<List<Recipe>>
    suspend fun getRecipe(token: String, recipeId: Int): Result<Recipe>
    fun <T, responseType> getResultFromResponse(
        response: Response<responseType>,
        mapper: (responseType) -> T
    ): Result<T> {
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.Success(mapper(it))
            } ?: Result.Failure(NoResponseException())
        } else {
            Result.Failure(NoResponseException())
        }
    }
}