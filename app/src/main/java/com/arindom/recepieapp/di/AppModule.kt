package com.arindom.recepieapp.di

import android.content.Context
import com.arindom.recepieapp.presentation.RecipeApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext app: Context): RecipeApplication {
        return app as RecipeApplication
    }
}