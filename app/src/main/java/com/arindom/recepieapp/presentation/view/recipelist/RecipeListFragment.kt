package com.arindom.recepieapp.presentation.view.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arindom.recepieapp.R
import com.arindom.recepieapp.domain.models.Recipe
import com.arindom.recepieapp.presentation.RecipeApplication
import com.arindom.recepieapp.presentation.components.*
import com.arindom.recepieapp.presentation.components.util.SnackbarController
import com.arindom.recepieapp.presentation.ui.RecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: RecipeApplication

    private val viewModel: RecipeListViewModel by viewModels()

    private val snackbarController = SnackbarController(lifecycleScope)

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext())
            .apply {
                setContent {
                    RecipeAppTheme(application.isDarkTheme.value) {
                        val recipesUiState = viewModel.recipesUiState.value
                        val scaffoldState = rememberScaffoldState()
                        val page = viewModel.page.value
                        Scaffold(
                            modifier = Modifier
                                .background(MaterialTheme.colors.background),
                            topBar = {
                                SearchAppBar(
                                    query = viewModel.query.value,
                                    selectedCategory = viewModel.selectedCategory.value,
                                    scrollStatePosition = viewModel.categoryScrollPosition,
                                    onQueryChange = viewModel::onQueryChange,
                                    executeSearch = { viewModel.onEventTriggered(RecipeListEvent.NewSearchEvent) },
                                    onCategorySelected = viewModel::onSelectedCategory,
                                    onChangeCategoryPosition = { position ->
                                        viewModel.categoryScrollPosition = position
                                    },
                                    onToggle = {
                                        application.toggleTheme()
                                    }
                                )
                            },
                            bottomBar = { BottomBar() },
                            drawerContent = { Drawer() },
                            scaffoldState = scaffoldState,
                            snackbarHost = {
                                scaffoldState.snackbarHostState
                            }
                        ) {
                            Column(modifier = Modifier.padding(it)) {
//                           PulsingDemo()
                                /*val buttonState =
                                    remember { mutableStateOf(AnimatedHeartButtonTransitionDefinition.HeartButtonState.IDLE) }
                                AnimationHeartButton(buttonState = buttonState, onToggle = {
                                    buttonState.value =
                                        if (buttonState.value == AnimatedHeartButtonTransitionDefinition.HeartButtonState.IDLE)
                                        AnimatedHeartButtonTransitionDefinition.HeartButtonState.ACTIVE else AnimatedHeartButtonTransitionDefinition.HeartButtonState.IDLE
                                })*/


                                /* Spacer(
                                     modifier = Modifier
                                         .padding(10.dp)
                                 )*/
                                RecipeList(
                                    recipeUiState = recipesUiState,
                                    page = page,
                                    scaffoldState = scaffoldState,
                                    onChangeRecipeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                                    onEventTriggered = viewModel::onEventTriggered,
                                    onListItemSelected = { recipe ->
                                        onRecipeSelected(recipe, scaffoldState)
                                    }
                                )
                            }
                        }
                    }
                }
            }
    }

    private fun onRecipeSelected(recipe: Recipe, scaffoldState: ScaffoldState) {
        if (recipe.id != null) {
            val bundle = Bundle()
            bundle.putInt("recipeId", recipe.id)
            findNavController().navigate(R.id.viewRecipe, bundle)
        } else {
            snackbarController.getScope().launch {
                snackbarController.showSnackbar(
                    scaffoldState = scaffoldState,
                    message = "Recipe Error",
                    actionLabel = "Ok",
                )
            }
        }
        /* snackbarController.getScope()
                            .launch {
                               snackbarController.showSnackbar(
                                     scaffoldState = scaffoldState,
                                     message = recipe.title ?: "",
                                     actionLabel = "Hide",
                                 )
                            }*/
    }
}


@Composable
fun BottomBar() {
    BottomNavigation(elevation = 12.dp) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Favorite) },
            selected = true,
            onClick = { })
        BottomNavigationItem(icon = { Icon(Icons.Default.Search) }, selected = false, onClick = { })
        BottomNavigationItem(
            icon = { Icon(Icons.Default.AccountBox) },
            selected = false,
            onClick = { })
    }
}

@Composable
fun Drawer() {
    Column() {
        Text(text = "item1")
        Text(text = "item2")
        Text(text = "item3")
        Text(text = "item4")
        Text(text = "item5")
        Text(text = "item6")
    }
}

@Composable
fun ShowSnackBar(

) {
}