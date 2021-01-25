package com.arindom.recepieapp.presentation.components

import androidx.compose.animation.transition
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arindom.recepieapp.presentation.animationdefinition.ShimmerAnimationDefinitions
import com.arindom.recepieapp.presentation.animationdefinition.ShimmerAnimationState


@Composable
fun LoadingRecipeListShimmer(
    imageHeight: Dp,
    padding: Dp = 16.dp
) {
    WithConstraints() {
        //Context to do the conversion from pixel to dp and vice-versa
        val cardWidthPx = with(AmbientDensity.current) {
            ((maxWidth - (padding * 2)).toPx())
        }

        val cardHeightPx = with(AmbientDensity.current) {
            (maxHeight - padding).toPx()
        }
        val cardShimmerDefinition = remember {
            ShimmerAnimationDefinitions(
                widthPx = cardWidthPx,
                heightPx = cardHeightPx,
            )
        }

        val cardShimmerAnimationTransitionState = transition(
            definition = cardShimmerDefinition.shimmerTranslateAnimation,
            initState = ShimmerAnimationState.START,
            toState = ShimmerAnimationState.END
        )

        val colors = listOf(
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.9f),
        )

        val xShimmerTranslateAnim =
            cardShimmerAnimationTransitionState[cardShimmerDefinition.xShimmerPropKey]
        val yShimmerTranslateAnim =
            cardShimmerAnimationTransitionState[cardShimmerDefinition.yShimmerPropKey]

        ScrollableColumn() {
            repeat(5) {
                ShimmerRecipeCardItem(
                    modifier = Modifier.padding(padding),
                    colors = colors,
                    cardHeight = imageHeight,
                    xShimmer = xShimmerTranslateAnim,
                    yShimmer = yShimmerTranslateAnim,
                    gradientWidth = cardShimmerDefinition.gradientWidth
                )
            }
        }
    }

}

@Composable
fun ShimmerRecipeCardItem(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    cardHeight: Dp,
    xShimmer: Float,
    yShimmer: Float,
    gradientWidth: Float
) {
    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset(xShimmer - gradientWidth, yShimmer - gradientWidth),
        end = Offset(xShimmer, yShimmer)
    )
    Column(modifier = modifier) {
        Surface(shape = MaterialTheme.shapes.small) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .preferredHeight(cardHeight)
                    .background(brush = brush)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Surface(shape = MaterialTheme.shapes.small) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .preferredHeight(cardHeight/10)
                    .background(brush = brush)
            )
        }

    }


}