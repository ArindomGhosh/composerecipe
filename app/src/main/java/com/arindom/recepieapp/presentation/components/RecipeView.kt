package com.arindom.recepieapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.arindom.recepieapp.domain.models.Recipe
import com.arindom.recepieapp.presentation.state.UiState
import com.arindom.recepieapp.util.LoadImage

const val IMAGE_HEIGHT = 260

@Composable
fun RecipeView(
    modifier: Modifier = Modifier,
    recipeUiState: UiState<Recipe>,
    snackbarAlert: (String, String) -> Unit
) {
    Box(modifier = modifier) {
        if (recipeUiState.loading) {
            LoadingRecipeShimmer(imageHeight = 300.dp)
        } else {
            recipeUiState.data?.let {
                RecipeDetails(recipe = it)
            } ?: snackbarAlert("An Error occurred", "Ok")
        }
        CircularIndeterminateProgressbar(isDisplayed = recipeUiState.loading)
    }
}

@Composable
fun RecipeDetails(
    modifier: Modifier = Modifier,
    recipe: Recipe
) {
    ScrollableColumn(modifier) {
        recipe.featuredImage?.let { url ->
            val imageBitmap = LoadImage(url = url, defaultImage = DEFAULT_IMAGE_RES)
                .value
            imageBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(IMAGE_HEIGHT.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                recipe.title?.let { title ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.h3
                        )
                        val rank = recipe.rating.toString()
                        Text(
                            text = rank,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.h5
                        )
                    }
                }
                recipe.publisher?.let { publisher ->
                    val updated = recipe.dateUpdated
                    Text(
                        text = if (updated != null) {
                            "Updated ${updated} by ${publisher}"
                        } else {
                            "By ${publisher}"
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        style = MaterialTheme.typography.caption
                    )
                }
                recipe.description?.let { description ->
                    if (description != "N/A") {
                        Text(
                            text = description,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
                for (ingredient in recipe.ingredients) {
                    Text(
                        text = ingredient,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
                recipe.cookingInstructions?.let { instructions ->
                    Text(
                        text = instructions,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}