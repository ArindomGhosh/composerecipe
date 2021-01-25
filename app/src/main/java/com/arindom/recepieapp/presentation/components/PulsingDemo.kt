package com.arindom.recepieapp.presentation.components

import android.util.FloatProperty
import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arindom.recepieapp.presentation.components.PulsingAnimationDefinition.pulseDefinition
import com.arindom.recepieapp.presentation.components.PulsingAnimationDefinition.PulseState
import com.arindom.recepieapp.presentation.components.PulsingAnimationDefinition.pulsePropKey


@Composable
fun PulsingDemo() {
    val color = MaterialTheme.colors.primary
    val pulseTransition = transition(
        initState = PulseState.INITIAL,
        toState = PulseState.FINALE,
        definition = pulseDefinition
    )
    val pulseMagnitude = pulseTransition[pulsePropKey]
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(pulseMagnitude.dp)
                .width(pulseMagnitude.dp),
            imageVector = Icons.Default.Favorite
        )
    }
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {
        drawCircle(radius = pulseMagnitude, color = color)
    }
}

object PulsingAnimationDefinition {
    enum class PulseState {
        INITIAL, FINALE
    }

    val pulsePropKey = FloatPropKey("pulseKey")

    val pulseDefinition = transitionDefinition<PulseState> {
        state(PulseState.INITIAL) { this[pulsePropKey] = 40f }
        state(PulseState.FINALE) { this[pulsePropKey] = 50f }

        transition(
            PulseState.INITIAL to PulseState.FINALE
        ) {
            pulsePropKey using infiniteRepeatable(
                tween(
                    delayMillis = 500,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        }
    }
}