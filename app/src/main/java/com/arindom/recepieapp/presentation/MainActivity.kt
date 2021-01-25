package com.arindom.recepieapp.presentation

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.arindom.recepieapp.R
import com.arindom.recepieapp.presentation.ui.RecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        /*
        CoroutineScope(IO).launch {
            println(
                recipeService.getRecipes(
                    "Token 9c8b06d329136da358c2d00e76946b0111ce2c48",
                    2,
                    "beef carrot potato onion"
                ).count
            )
        }*/
        /*setContent {
            RecepieappTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MealDetailsColumn()
                }
            }
        }*/
    }
}

@Composable
private fun MealDetailsRow() {
    ScrollableColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff2f2f2))
    ) {
        ShowImageFromRes(resId = R.drawable.happy_meal_small)
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Happy Meal",
                    style = TextStyle(
                        fontSize = TextUnit.Companion.Sp(26)
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = "$5.99",
                    style = TextStyle(
                        color = Color(0xff85bb65),
                        fontSize = TextUnit.Companion.Sp(17)
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "800 calories",
                style = TextStyle(
                    fontSize = TextUnit.Companion.Sp(17)
                )
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Button(
                onClick = {},
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(text = "Order Now")
            }
        }
    }
}

@Composable
private fun MealDetailsColumn() {
    ScrollableColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff2f2f2))
    ) {
        ShowImageFromRes(resId = R.drawable.happy_meal_small)
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Happy Meal",
                style = TextStyle(
                    fontSize = TextUnit.Companion.Sp(26)
                )
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "800 calories",
                style = TextStyle(
                    fontSize = TextUnit.Companion.Sp(17)
                )
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = "$5.99",
                style = TextStyle(
                    color = Color(0xff85bb65),
                    fontSize = TextUnit.Companion.Sp(17)
                )
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
private fun ShowImageFromRes(@DrawableRes resId: Int) {
    Image(
        bitmap = imageResource(resId),
        modifier = Modifier.height(300.dp),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
private fun MealDetailsRowPreview() {
    RecipeAppTheme {
        MealDetailsRow()
    }
}

@Preview(showBackground = true)
@Composable
fun MealDetailsColumnPreview() {
    RecipeAppTheme {
        MealDetailsColumn()
    }
}