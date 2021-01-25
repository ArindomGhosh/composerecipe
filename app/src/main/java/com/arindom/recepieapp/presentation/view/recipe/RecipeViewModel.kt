package com.arindom.recepieapp.presentation.view.recipe

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arindom.recepieapp.domain.models.Recipe
import com.arindom.recepieapp.repository.RecipeRepository
import com.arindom.recepieapp.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

const val STATE_KEY_RECIPE = "recipe.state.recipe.key"

class RecipeViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val recipe = mutableStateOf<Recipe?>(null)
    val loading = mutableStateOf(false)

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
        loading.value = true
        delay(1000)
        val result = repository.getRecipe(
            token = token,
            recipeId = recipeId
        )
        recipe.value = result
        loading.value = false
        savedStateHandle.set(STATE_KEY_RECIPE, recipeId)
    }

}