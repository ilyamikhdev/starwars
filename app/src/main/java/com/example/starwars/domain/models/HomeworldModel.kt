package com.example.starwars.domain.models

import com.example.starwars.PersonByIdQuery

data class HomeworldModel(
    val id: String,
    val name: String?,
    val population: Double?,
    val gravity: String?,
    val orbitalPeriod: Int?,
    val rotationPeriod: Int?,
    val surfaceWater: Double?
)

fun PersonByIdQuery.Homeworld.mapToHomeworldModel(): HomeworldModel {
    return HomeworldModel(
        id = this.id,
        name = this.name,
        population = this.population,
        gravity = this.gravity,
        orbitalPeriod = this.orbitalPeriod,
        rotationPeriod = this.rotationPeriod,
        surfaceWater = this.surfaceWater,
    )
}