package com.arindom.recepieapp.presentation.view.recipelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arindom.recepieapp.domain.models.Recipe
import com.arindom.recepieapp.repository.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

const val PAGE_SIZE = 30

class RecipeListViewModel
@ViewModelInject constructor(
    private val recipeRepository: RecipeRepository,
    @Named("auth_token") private val token: String
) : ViewModel() {
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory = mutableStateOf<FoodCategory?>(null)
    var categoryScrollPosition = 0f
    var loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0

    init {
        newSearch()
    }

    fun newSearch() {
        viewModelScope.launch {
            loading.value = true
            resetSearchState()
            delay(2000)
            val result = recipeRepository.getRecipes(
                token,
                1,
                query.value
            )
            recipes.value = result
            loading.value = false
        }
    }

    fun nextPage() {
        // prevent duplicate event due to recompose happening to quickly
        viewModelScope.launch {
            if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                loading.value = true
                incrementPage()
                //show loading
                delay(1000)
                if (page.value > 1) {
                    val results = recipeRepository.getRecipes(
                        token = token,
                        query = query.value,
                        page = page.value
                    )
                    appendRecipeList(results)
                }
                loading.value = false
            }
        }
    }

    /*
    * Append new recipes to the current recipe list
    * */
    private fun appendRecipeList(newRecipe: List<Recipe>) {
        val current = ArrayList(recipes.value)
        current.addAll(newRecipe)
        recipes.value = current
    }


    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    fun onSelectedCategory(category: String) {
        selectedCategory.value = getFoodCategory(category)
        onQueryChange(category)
    }
}