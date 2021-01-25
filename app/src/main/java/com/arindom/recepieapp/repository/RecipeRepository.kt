package com.arindom.recepieapp.repository

import com.arindom.recepieapp.domain.models.Recipe

interface RecipeRepository {
    suspend fun getRecipes(tokes: String, page: Int, query: String): List<Recipe>
    suspend fun getRecipe(tokes: String, recipeId: Int): Recipe
}