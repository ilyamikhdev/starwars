package com.example.starwars.domain

import com.example.starwars.domain.models.PersonModel

sealed class PeopleResult {
    data class Success(val items: List<PersonModel>) : PeopleResult()
    data class Error(val text: String) : PeopleResult()
}