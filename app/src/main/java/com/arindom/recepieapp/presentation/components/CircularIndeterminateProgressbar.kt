package com.arindom.recepieapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.arindom.recepieapp.presentation.ui.RecipeAppTheme


@Composable
fun CircularIndeterminateProgressbar(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (progressBar, text) = createRefs()
            val topGuideline = createGuidelineFromTop(0.3f)
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressBar) {
                    top.linkTo(topGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                color = MaterialTheme.colors.primary
            )
            Text(
                text = "Loading . . .",
                color = Color.Black,
                fontSize = TextUnit.Companion.Sp(15),
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(progressBar.bottom)
                    start.linkTo(progressBar.start)
                    end.linkTo(progressBar.end)
                }
            )
        }

    }
}

@Composable
fun CircularIndeterminateProgressbarWithConstraint(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        WithConstraints(modifier = Modifier.fillMaxSize()) {
            val constraintSet = if (minWidth < 600.dp) {
                getConstraintSetFor(0.3f)
            } else {
                getConstraintSetFor(0.7f)
            }
            ConstraintLayout(
                modifier = Modifier.fillMaxSize(),
                constraintSet = constraintSet
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.layoutId("progressBar"),
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = "Loading . . .",
                    color = Color.Black,
                    fontSize = TextUnit.Companion.Sp(15),
                    modifier = Modifier.layoutId("loadingText"),
                )
            }
        }


    }
}

private fun getConstraintSetFor(verticalBias: Float): ConstraintSet {
    return ConstraintSet {
        val guideline = createGuidelineFromTop(verticalBias)
        val progressRef = createRefFor("progressBar")
        val textRef = createRefFor("loadingText")
        constrain(progressRef) {
            top.linkTo(guideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(textRef) {
            top.linkTo(progressRef.bottom)
            start.linkTo(progressRef.start)
            end.linkTo(progressRef.end)
        }
    }
}

@Composable
@Preview
fun CircularIndeterminateProgressbarPreview() {
    RecipeAppTheme {
        CircularIndeterminateProgressbar(isDisplayed = true)
    }
}

@Composable
@Preview
fun CircularIndeterminateProgressbarWithConstraintPreview() {
    RecipeAppTheme {
        CircularIndeterminateProgressbarWithConstraint(isDisplayed = true)
    }
}

