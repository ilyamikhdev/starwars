package com.example.starwars.ui.screens.person

import com.example.starwars.domain.models.PersonExtendedModel
import com.example.starwars.ui.common.UIState

data class PersonState(
    val state: UIState = UIState.DATA,
    val data: PersonExtendedModel? = null,
    val error: String? = null,
    val showDialog: Boolean = false
)