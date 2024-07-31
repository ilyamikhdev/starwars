package com.example.starwars.domain.models

import com.example.starwars.AllPeopleQuery

data class PersonModel(
    val id: String,
    val name: String?,
    val mass: Double?,
    val height: Int?,
)

fun List<AllPeopleQuery.Person?>?.mapToPersonModelList(): List<PersonModel> {
    return this?.map {
        PersonModel(
            id = it?.id ?: "",
            name = it?.name,
            mass = it?.mass,
            height = it?.height
        )
    } ?: emptyList()
}