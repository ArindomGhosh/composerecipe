package com.arindom.recepieapp.presentation.components.util

import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackbarController(
    private val scope: CoroutineScope
) {
    private var snackbarJob: Job? = null

    fun getScope() = scope

    init {
        cancelActiveJob()
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun showSnackbar(
        scaffoldState: ScaffoldState,
        message: String,
        actionLabel: String
    ) {
        if (snackbarJob == null) {
            snackbarJob = scope.launch {
                scaffoldState
                    .snackbarHostState
                    .showSnackbar(message, actionLabel)
                cancelActiveJob()
            }

        } else {
            cancelActiveJob()
            snackbarJob = scope.launch {
                scaffoldState
                    .snackbarHostState
                    .showSnackbar(message, actionLabel)
                cancelActiveJob()
            }
        }

    }

    private fun cancelActiveJob() {
        snackbarJob?.let { job ->
            job.cancel()
            snackbarJob = Job()
        }
    }
}