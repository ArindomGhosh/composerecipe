package com.arindom.recepieapp.presentation.components

import androidx.compose.animation.ColorPropKey
import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.TransitionState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.arindom.recepieapp.R
import com.arindom.recepieapp.presentation.components.AnimatedHeartButtonTransitionDefinition.HEAR_SIZE
import com.arindom.recepieapp.presentation.components.AnimatedHeartButtonTransitionDefinition.HeartButtonState
import com.arindom.recepieapp.presentation.components.AnimatedHeartButtonTransitionDefinition.heartSizePropKey
import com.arindom.recepieapp.presentation.components.AnimatedHeartButtonTransitionDefinition.heartTransitionDefinition
import com.arindom.recepieapp.util.LoadImage

@Composable
fun AnimationHeartButton(
    modifier: Modifier = Modifier,
    buttonState: MutableState<HeartButtonState>,
    onToggle: () -> Unit
) {
    val toState =
        if (buttonState.value == HeartButtonState.IDLE) HeartButtonState.ACTIVE else HeartButtonState.IDLE
    val transitionState = transition(
        initState = buttonState.value,
        toState = toState,
        definition = heartTransitionDefinition,
    )
    HeartButton(
        modifier = modifier,
        buttonState = buttonState,
        state = transitionState,
        onToggle = onToggle
    )
}

@Composable
private fun HeartButton(
    modifier: Modifier = Modifier,
    buttonState: MutableState<HeartButtonState>,
    state: TransitionState,
    onToggle: () -> Unit
) {
    if (buttonState.value == HeartButtonState.ACTIVE) {
        LoadImage(drawable = R.drawable.heart_red).value
            ?.let { imageBitmap ->
                Image(
                    imageBitmap.asImageBitmap(),
                    modifier = modifier
                        .clickable(onClick = onToggle, indication = null)
                        .height(state[heartSizePropKey])
                        .width(state[heartSizePropKey])
                )
            }
    } else {
        LoadImage(drawable = R.drawable.heart_grey).value
            ?.let { imageBitmap ->
                Image(
                    imageBitmap.asImageBitmap(),
                    modifier = modifier
                        .clickable(onClick = onToggle, indication = null)
                        .height(state[heartSizePropKey])
                        .width(state[heartSizePropKey])
                )
            }
    }
}

object AnimatedHeartButtonTransitionDefinition {
    const val HEART_COLOR = "heartColor"
    const val HEAR_SIZE = "heartSize"

    enum class HeartButtonState {
        IDLE, ACTIVE
    }

    val idleHeartSize = 50.dp
    val expandedHeartSize = 80.dp

    val heartColorPropKey = ColorPropKey(label = HEART_COLOR)
    val heartSizePropKey = DpPropKey(label = HEAR_SIZE)

    val heartTransitionDefinition = transitionDefinition<HeartButtonState> {
        state(HeartButtonState.IDLE) {
            this[heartColorPropKey] = Color.LightGray
            this[heartSizePropKey] = idleHeartSize
        }

        state(HeartButtonState.ACTIVE) {
            this[heartColorPropKey] = Color.Red
            this[heartSizePropKey] = idleHeartSize
        }

        transition(HeartButtonState.IDLE to HeartButtonState.ACTIVE) {
            heartColorPropKey using tween(durationMillis = 500)
            heartSizePropKey using keyframes {
                durationMillis = 500
                expandedHeartSize at 100
                idleHeartSize at 200
            }
        }

        transition(HeartButtonState.ACTIVE to HeartButtonState.IDLE) {
            heartColorPropKey using tween(durationMillis = 500)
            heartSizePropKey using keyframes {
                durationMillis = 500
                expandedHeartSize at 100
                idleHeartSize at 200
            }
        }
    }
}