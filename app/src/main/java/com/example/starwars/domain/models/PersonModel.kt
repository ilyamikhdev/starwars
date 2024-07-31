package com.example.starwars.domain.models

import com.example.starwars.AllPeopleQuery

data class PersonModel(
    val id: String,
    val name: String?,
    val height: Int?,
    val mass: Double?
)

fun List<AllPeopleQuery.Person?>?.mapToPersonModelList(): List<PersonModel> {
    return this?.map {
        PersonModel(
            id = it?.id ?: "",
            name = it?.name,
            height = it?.height,
            mass = it?.mass
        )
    } ?: emptyList()
}