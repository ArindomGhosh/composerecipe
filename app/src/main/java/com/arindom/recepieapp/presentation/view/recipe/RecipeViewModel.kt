package com.arindom.recepieapp.presentation.view.recipe

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arindom.recepieapp.domain.Result
import com.arindom.recepieapp.domain.models.Recipe
import com.arindom.recepieapp.presentation.state.UiState
import com.arindom.recepieapp.presentation.state.copyWithResult
import com.arindom.recepieapp.repository.RecipeRepository
import com.arindom.recepieapp.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

const val STATE_KEY_RECIPE = "recipe.state.recipe.key"

class RecipeViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val recipeUiState = mutableStateOf<UiState<Recipe>>(UiState())

    init {
        savedStateHandle.get<Int>(STATE_KEY_RECIPE)?.let { recipeId ->
            onTriggerEvent(RecipeEvents.GetRecipeEvent(recipeId))
        }
    }

    fun onTriggerEvent(recipeEvents: RecipeEvents) {
        viewModelScope.launch {
            try {
                when (recipeEvents) {
                    is RecipeEvents.GetRecipeEvent -> getRecipe(recipeEvents.id)
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }

    }

    private suspend fun getRecipe(recipeId: Int) {
        recipeUiState.value = UiState(loading = true)
        delay(1000)
        val result = repository.getRecipe(
            token = token,
            recipeId = recipeId
        )
        recipeUiState.value =recipeUiState.value.copyWithResult(result)
        savedStateHandle.set(STATE_KEY_RECIPE, recipeId)
    }

}