package com.arindom.recepieapp.presentation.animationdefinition

import androidx.compose.animation.core.*

const val XSHIMMER = "xShimmer"
const val YSHIMMER = "yShimmer"
enum class ShimmerAnimationState {
    START, END
}
class ShimmerAnimationDefinitions(
    private val widthPx: Float,
    private val heightPx: Float,
    private val animationDuration: Int = 1300,
    private val animationDelay: Int = 300
) {
    var gradientWidth: Float = (0.2f * heightPx)
    val xShimmerPropKey = FloatPropKey(XSHIMMER)
    val yShimmerPropKey = FloatPropKey(YSHIMMER)

    val shimmerTranslateAnimation = transitionDefinition<ShimmerAnimationState> {
        state(ShimmerAnimationState.START) {
            this[xShimmerPropKey] = 0f
            this[yShimmerPropKey] = 0f
        }
        state(ShimmerAnimationState.END) {
            this[xShimmerPropKey] = widthPx + gradientWidth
            this[yShimmerPropKey] = heightPx + gradientWidth
        }

        transition(ShimmerAnimationState.START to ShimmerAnimationState.END) {
            xShimmerPropKey using infiniteRepeatable(
                animation = tween(
                    durationMillis = animationDuration,
                    easing = LinearEasing,
                    delayMillis = animationDelay
                ),
                repeatMode = RepeatMode.Restart
            )
            yShimmerPropKey using infiniteRepeatable(
                animation = tween(
                    durationMillis = animationDuration,
                    easing = LinearEasing,
                    delayMillis = animationDelay
                ),
                repeatMode = RepeatMode.Restart
            )
        }
    }


}