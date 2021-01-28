package com.arindom.recepieapp.repository

import com.arindom.recepieapp.domain.Result
import com.arindom.recepieapp.domain.exceptions.NoResponseException
import com.arindom.recepieapp.domain.models.Recipe
import com.arindom.recepieapp.network.models.RecipeDtoMapper
import com.arindom.recepieapp.network.services.RecipeService
import retrofit2.Response

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {
    override suspend fun getRecipes(token: String, page: Int, query: String): Result<List<Recipe>> {
        val result = recipeService.getRecipes(token, page, query)
        return getResultFromResponse(result) {
            mapper.toDomainList(it.recipes)
        }
    }

    override suspend fun getRecipe(token: String, recipeId: Int): Result<Recipe> {
        val result = recipeService.getRecipeForId(token, recipeId)
        return getResultFromResponse(result) {
            mapper.mapToDomainModel(it)
        }
    }
}