package com.example.starwars.ui.screens.people

import com.example.starwars.domain.models.PersonModel
import com.example.starwars.ui.common.UIState

data class PeopleState(
    val state: UIState = UIState.LOADING,
    val data: List<PersonModel> = emptyList(),
    val error: String? = null,
)