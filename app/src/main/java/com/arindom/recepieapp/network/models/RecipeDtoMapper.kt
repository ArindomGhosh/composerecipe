package com.arindom.recepieapp.network.models

import com.arindom.recepieapp.domain.models.Recipe
import com.arindom.recepieapp.domain.util.DomainMapper

class RecipeDtoMapper : DomainMapper<RecipeDto, Recipe> {
    override fun mapToDomainModel(entity: RecipeDto): Recipe {
        return Recipe(
            id = entity.pk,
            title = entity.title,
            publisher = entity.publisher,
            featuredImage = entity.featuredImage,
            rating = entity.rating,
            sourceUrl = entity.sourceURL,
            description = entity.description,
            cookingInstructions = entity.cookingInstructions,
            ingredients = entity.ingredients,
            dateAdded = entity.dateAdded,
            dateUpdated = entity.dateUpdated,
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
            pk = domainModel.id ?: 0,
            title = domainModel.title ?: "",
            featuredImage = domainModel.featuredImage ?: "",
            rating = domainModel.rating ?: 0,
            publisher = domainModel.publisher ?: "",
            sourceURL = domainModel.sourceUrl ?: "",
            description = domainModel.description ?: "",
            cookingInstructions = domainModel.cookingInstructions ?: "",
            ingredients = domainModel.ingredients,
            dateAdded = domainModel.dateAdded ?: "",
            dateUpdated = domainModel.dateUpdated ?: "",
        )
    }

    fun toDomainList(initial: List<RecipeDto>): List<Recipe> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Recipe>): List<RecipeDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}