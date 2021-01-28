package com.arindom.recepieapp.presentation.view.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arindom.recepieapp.presentation.RecipeApplication
import com.arindom.recepieapp.presentation.components.RecipeView
import com.arindom.recepieapp.presentation.components.util.SnackbarController
import com.arindom.recepieapp.presentation.ui.RecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {
    @Inject
    lateinit var application: RecipeApplication

    private val snackbarController = SnackbarController(lifecycleScope)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { recipeId ->
            recipeViewModel.onTriggerEvent(RecipeEvents.GetRecipeEvent(recipeId))
        }
    }

    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext())
            .apply {
                setContent {
                    val scaffoldState = rememberScaffoldState()
                    val recipeUiState = recipeViewModel.recipeUiState.value
                    RecipeAppTheme(darkTheme = application.isDarkTheme.value) {
                        Scaffold(scaffoldState = scaffoldState,
                            snackbarHost = { scaffoldState.snackbarHostState }) {
                            RecipeView(
                                modifier = Modifier.padding(padding = PaddingValues()),
                                recipeUiState = recipeUiState,
                                snackbarAlert = { message, actionLabel ->
                                    snackbarController.getScope()
                                        .launch {
                                            snackbarController.showSnackbar(
                                                scaffoldState = scaffoldState,
                                                message = message,
                                                actionLabel = actionLabel
                                            )
                                        }
                                }
                            )
                        }
                    }


                }
            }
    }
}