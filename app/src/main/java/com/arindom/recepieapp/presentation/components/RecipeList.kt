package com.arindom.recepieapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arindom.recepieapp.domain.models.Recipe
import com.arindom.recepieapp.presentation.state.UiState
import com.arindom.recepieapp.presentation.view.recipelist.PAGE_SIZE
import com.arindom.recepieapp.presentation.view.recipelist.RecipeListEvent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeList(
    modifier: Modifier = Modifier,
    recipeUiState: UiState<List<Recipe>>,
    page: Int,
    scaffoldState: ScaffoldState,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    onEventTriggered: (RecipeListEvent) -> Unit,
    onListItemSelected: (Recipe) -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (recipeUiState.loading && recipeUiState.data.isNullOrEmpty()) {
            LoadingRecipeListShimmer(imageHeight = 300.dp)
        } else {
            LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                itemsIndexed(recipeUiState.data!!) { index, recipe ->
                    onChangeRecipeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !recipeUiState.loading) {
                        onEventTriggered(RecipeListEvent.NextPageEvent)
                    }

                    RecipeCard(recipe = recipe, onClick = {
                        onListItemSelected(recipe)
                    })
                }
            }
        }
        CircularIndeterminateProgressbar(isDisplayed = recipeUiState.loading)
        SnackbarHostDemo(
            modifier = Modifier.align(Alignment.BottomCenter),
            snackbarHostState = scaffoldState.snackbarHostState
        )
    }
}