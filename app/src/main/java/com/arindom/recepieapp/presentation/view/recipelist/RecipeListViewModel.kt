package com.arindom.recepieapp.presentation.view.recipelist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arindom.recepieapp.domain.models.Recipe
import com.arindom.recepieapp.repository.RecipeRepository
import com.arindom.recepieapp.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

const val PAGE_SIZE = 30
//https://developer.android.com/reference/androidx/lifecycle/SavedStateHandle

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

class RecipeListViewModel
@ViewModelInject constructor(
    private val recipeRepository: RecipeRepository,
    @Named("auth_token") private val token: String,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory = mutableStateOf<FoodCategory?>(null)
    var categoryScrollPosition = 0f
    var loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            Log.d(TAG, "restoring page: ${p}")
            setPage(p)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            Log.d(TAG, "restoring scroll position: ${p}")
            setListScrollPosition(p)
        }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
            setSelectedCategory(c)
        }

        // Were they doing something before the process died?
        if (recipeListScrollPosition != 0) {
            onEventTriggered(RecipeListEvent.RestoreStateEvent)
        } else {
            onEventTriggered(RecipeListEvent.NewSearchEvent)
        }

    }

    fun onEventTriggered(recipeListEvent: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (recipeListEvent) {
                    RecipeListEvent.NewSearchEvent -> newSearch()
                    RecipeListEvent.NextPageEvent -> nextPage()
                    RecipeListEvent.RestoreStateEvent -> TODO()
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    // use case 1
    private suspend fun newSearch() {
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


    //use case 2
    private suspend fun nextPage() {
        // prevent duplicate event due to recompose happening to quickly
        Log.d(TAG, "recipeListScrollPosition: ${recipeListScrollPosition + 1}")
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()
            //show loading
            delay(1000)
            Log.d(TAG, "nextPage: ${page.value}")
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

    private suspend fun restoreState() {
        loading.value = true
        //it should be cached in local db for fetching recipe list
        val result = mutableListOf<Recipe>()
        for (page in 1..page.value) {
            result.addAll(
                recipeRepository.getRecipes(
                    token,
                    page,
                    query.value
                )
            )
            if (page == this.page.value) {
                recipes.value = result
                loading.value = false
            }
        }
    }

    /*
    * Append new recipes to the current recipe list
    * */
    private fun appendRecipeList(newRecipe: List<Recipe>) {
        Log.d(TAG, "appendRecipeList: ${newRecipe.size}")
        val current = ArrayList(recipes.value)
        current.addAll(newRecipe)
        recipes.value = current
    }


    private fun incrementPage() {
        setPage(page.value + 1)
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        setListScrollPosition(position = position)
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
        setSelectedCategory(null)
    }

    fun onQueryChange(newQuery: String) {
        setQuery(newQuery)
    }

    fun onSelectedCategory(category: String) {
        setSelectedCategory(getFoodCategory(category))
        onQueryChange(category)
    }

    /*
    * save in savedStateHandle to retrieve in case of process kill
    * */
    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }
}