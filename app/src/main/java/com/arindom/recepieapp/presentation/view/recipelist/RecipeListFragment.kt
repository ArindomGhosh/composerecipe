package com.arindom.recepieapp.presentation.view.recipelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.arindom.recepieapp.R
import com.arindom.recepieapp.presentation.RecipeApplication
import com.arindom.recepieapp.presentation.components.*
import com.arindom.recepieapp.presentation.components.util.SnackbarController
import com.arindom.recepieapp.presentation.ui.RecipeAppTheme
import com.arindom.recepieapp.util.TAG
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
                        val recipes = viewModel.recipes.value
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
                                    executeSearch = viewModel::newSearch,
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
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    if (viewModel.loading.value && recipes.isEmpty()) {
                                        LoadingRecipeListShimmer(imageHeight = 300.dp)
                                    } else {
                                        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                                            itemsIndexed(recipes) { index, item ->
                                                viewModel.onChangeRecipeScrollPosition(index)
                                                if ((index + 1) >= (page * PAGE_SIZE) && !viewModel.loading.value) {
                                                    viewModel.nextPage()
                                                }

                                                RecipeCard(recipe = item, onClick = {
                                                    lifecycleScope.launch {
                                                        snackbarController.getScope()
                                                            .launch {
                                                                snackbarController.showSnackbar(
                                                                    scaffoldState = scaffoldState,
                                                                    message = item.title ?: "",
                                                                    actionLabel = "Hide",
                                                                )
                                                            }
                                                        /* scaffoldState
                                                             .snackbarHostState
                                                             .showSnackbar(
                                                                 message = item.title ?: "",
                                                                 actionLabel = "Hide",
                                                                 duration = SnackbarDuration.Short
                                                             )*/
                                                    }
                                                })
                                            }
                                        }
                                    }
                                    CircularIndeterminateProgressbar(isDisplayed = viewModel.loading.value)
                                    SnackbarHostDemo(
                                        modifier = Modifier.align(Alignment.BottomCenter),
                                        snackbarHostState = scaffoldState.snackbarHostState
                                    )
                                }
                            }
                        }
                    }
                }
            }
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