package com.arindom.recepieapp.repository

import com.arindom.recepieapp.domain.models.Recipe
import com.arindom.recepieapp.network.models.RecipeDtoMapper
import com.arindom.recepieapp.network.services.RecipeService

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {
    override suspend fun getRecipes(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(recipeService.getRecipes(token, page, query).recipes)
    }

    override suspend fun getRecipe(token: String, recipeId: Int): Recipe {
        return mapper.mapToDomainModel(recipeService.getRecipeForId(token, recipeId))
    }
}