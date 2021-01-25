package com.arindom.recepieapp.repository

import com.arindom.recepieapp.domain.models.Recipe

interface RecipeRepository {
    suspend fun getRecipes(token: String, page: Int, query: String): List<Recipe>
    suspend fun getRecipe(token: String, recipeId: Int): Recipe
}