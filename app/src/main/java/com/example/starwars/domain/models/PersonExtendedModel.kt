package com.example.starwars.domain.models

import com.example.starwars.PersonByIdQuery

data class PersonExtendedModel(
    val id: String,
    val name: String?,
    val homeworld: HomeworldModel?
)

fun PersonByIdQuery.Person.mapToPersonExtendedModel(): PersonExtendedModel {
    return PersonExtendedModel(
        id = this.id,
        name = this.name,
        homeworld = this.homeworld?.mapToHomeworldModel()
    )
}