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

    private fun resetSearchState() {
        recipes.value = listOf()
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