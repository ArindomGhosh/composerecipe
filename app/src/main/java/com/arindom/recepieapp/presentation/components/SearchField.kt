package com.arindom.recepieapp.presentation.components

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arindom.recepieapp.presentation.ui.RecipeAppTheme
import com.arindom.recepieapp.presentation.view.recipelist.FoodCategory
import com.arindom.recepieapp.presentation.view.recipelist.getAllFoodCategories

const val SEARCH_LABEL = "Search"


@Composable
fun SearchAppBar(
    query: String,
    selectedCategory: FoodCategory?,
    scrollStatePosition: Float,
    onQueryChange: (String) -> Unit,
    executeSearch: () -> Unit,
    onCategorySelected: (String) -> Unit,
    onChangeCategoryPosition: (Float) -> Unit,
    onToggle: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        Column {
            val scrollState = rememberScrollState(scrollStatePosition)
            Row(modifier = Modifier.fillMaxWidth()) {
                SearchTextField(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    initialQuery = query,
                    onValueChange = { newValue ->
                        onQueryChange(newValue)
                    },
                    onImeActionPerformed = { imeAction, softwareKeyboardController ->
                        if (imeAction == ImeAction.Search) {
                            executeSearch()
                            softwareKeyboardController?.hideSoftwareKeyboard()
                        }
                    }
                )
                ConstraintLayout(modifier = Modifier.align(Alignment.CenterVertically)) {
                    val (menuRef) = createRefs()
                    IconButton(
                        modifier = Modifier.constrainAs(menuRef) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                        onClick = onToggle
                    ) {
                        Icon(Icons.Filled.MoreVert)
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            ScrollableRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 8.dp),
                scrollState = scrollState
            ) {
                scrollState.scrollTo(scrollStatePosition)
                getAllFoodCategories().forEach { foodCategory ->
                    FoodCategoryChip(
                        category = foodCategory.value,
                        isSelected = selectedCategory == foodCategory,
                        onSelectedCategoryChanged = { newCategory ->
                            onCategorySelected(newCategory)
                            onChangeCategoryPosition(scrollState.value)
                        },
                        onExecuteSearch = { executeSearch() }
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}


@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    initialQuery: String = "",
    onValueChange: (String) -> Unit,
    onImeActionPerformed: (ImeAction, SoftwareKeyboardController?) -> Unit
) {
    TextField(
        modifier = modifier,
        value = initialQuery,
        onValueChange = onValueChange,
        label = { Text(text = SEARCH_LABEL) },
        placeholder = { Text(text = SEARCH_LABEL) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        onImeActionPerformed = onImeActionPerformed,
        leadingIcon = { Icon(Icons.Filled.Search) },
        textStyle =  TextStyle(color = MaterialTheme.colors.onSurface),
        backgroundColor = MaterialTheme.colors.surface
    )
}


@Composable
@Preview
fun SearchAppBarPreview() {
    RecipeAppTheme {
        SearchAppBar("new", FoodCategory.CHICKEN, 0F, {}, {}, {}, {},{})
    }
}

@Composable
@Preview
fun SearchTextFieldPreview() {
    RecipeAppTheme {
        SearchTextField(
            initialQuery = "Search",
            onValueChange = {},
            onImeActionPerformed = { _, _ -> })
    }
}