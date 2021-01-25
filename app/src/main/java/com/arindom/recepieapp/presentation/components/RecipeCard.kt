package com.arindom.recepieapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.CoreTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arindom.recepieapp.R
import com.arindom.recepieapp.domain.models.Recipe
import com.arindom.recepieapp.presentation.ui.RecipeAppTheme
import com.arindom.recepieapp.util.LoadImage

const val DEFAULT_IMAGE_RES = R.drawable.empty_plate

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(top = 6.dp, bottom = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp
    ) {
        Column {
            recipe.featuredImage?.let { url ->
                val imageBitmap = LoadImage(url = url, defaultImage = DEFAULT_IMAGE_RES)
                imageBitmap.value?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        modifier = Modifier
                            .preferredHeight(225.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                recipe.title?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 8.dp,
                                vertical = 12.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.h3
                        )
                        Text(
                            text = recipe.rating.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.End)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.h5
                        )
                    }
                }

            }
        }
    }

}

@Composable
@InternalTextApi
fun testingTextField() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(
                16.dp
            )
    ) {
        TextField(value = "textField", onValueChange = { /*TODO*/ })
        OutlinedTextField(value = "Outlined", onValueChange = { /*TODO*/ })
        BasicTextField(value = "Basic TextField", onValueChange = { /*TODO*/ })
        CoreTextField(value = TextFieldValue("Core text"), onValueChange = { /*TODO*/ })
    }
}

@Composable
@Preview
@InternalTextApi
fun TestingTextPreview() {
    RecipeAppTheme {
        testingTextField()
    }
}

@Composable
@Preview
fun RecipeCardPreview() {
    RecipeAppTheme {
        RecipeCard(
            recipe = Recipe(featuredImage = "some Url", title = "my recipe", rating = 86),
            onClick = { })
    }
}