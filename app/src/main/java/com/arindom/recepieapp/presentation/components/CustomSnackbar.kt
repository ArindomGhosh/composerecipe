package com.arindom.recepieapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.google.android.material.snackbar.Snackbar

@Composable
fun ShowSnackbar(
    message: String,
    hideSnackbar: () -> Unit
) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val snackbarRef = createRef()
        Snackbar(modifier = Modifier.constrainAs(snackbarRef) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        },
            action = {
                Text(
                    text = "Hide",
                    modifier = Modifier.clickable(onClick = hideSnackbar),
                    style = MaterialTheme.typography.h5
                )
            }) {
            Text(text = message)
        }

    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SnackbarHostDemo(
    modifier: Modifier,
    snackbarHostState: SnackbarHostState
) {
    SnackbarHost(
        modifier = modifier,
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = { snackbarHostState.currentSnackbarData?.dismiss() }) {
                        Text(
                            text = data.actionLabel ?: "",
                            style = TextStyle(color = Color.White)
                        )
                    }
                }) {
                Text(text = data.message ?: "")
            }
        }
    )


}