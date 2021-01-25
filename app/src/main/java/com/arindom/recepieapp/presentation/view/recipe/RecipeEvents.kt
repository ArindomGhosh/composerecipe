package com.arindom.recepieapp.presentation.view.recipe

sealed class RecipeEvents {
    data class GetRecipeEvent(val id: Int) : RecipeEvents()
}