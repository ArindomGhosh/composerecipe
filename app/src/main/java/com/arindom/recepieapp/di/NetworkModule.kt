package com.arindom.recepieapp.di

import com.arindom.recepieapp.network.models.RecipeDtoMapper
import com.arindom.recepieapp.network.services.RecipeService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRecipeMapper(): RecipeDtoMapper {
        return RecipeDtoMapper()
    }

    @BaseUrl
    @Singleton
    @Provides
    fun providesBaseUrl():String{
        return "https://food2fork.ca/api/recipe/"
    }

    @Singleton
    @Provides
    fun providesRetrofit(@BaseUrl baseUrl:String):Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeService(retrofit: Retrofit): RecipeService {
        return retrofit
            .create(RecipeService::class.java)
    }

    @Singleton
    @Provides
    @Named("auth_token")
    fun providesAuthToken():String{
        return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }

}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl