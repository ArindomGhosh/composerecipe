package com.arindom.recepieapp.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RecipeApplication : Application() {
    val isDarkTheme =  mutableStateOf(false)
    fun toggleTheme(){
        isDarkTheme.value = !isDarkTheme.value
    }
}