package com.arindom.recepieapp.di

import com.arindom.recepieapp.network.models.RecipeDtoMapper
import com.arindom.recepieapp.network.services.RecipeService
import com.arindom.recepieapp.repository.RecipeRepository
import com.arindom.recepieapp.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        mapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeService, mapper)
    }
}