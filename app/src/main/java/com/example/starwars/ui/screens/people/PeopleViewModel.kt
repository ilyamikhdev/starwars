package com.example.starwars.ui.screens.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.domain.RequestResult
import com.example.starwars.domain.usecases.GetPeopleUseCase
import com.example.starwars.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val getPeopleUseCase: GetPeopleUseCase
) : ViewModel() {
    private val _screenState = MutableStateFlow(PeopleState())
    val screenState: StateFlow<PeopleState> = _screenState.asStateFlow()

    init {
        loadPeople()
    }

    private fun loadPeople() {
        _screenState.update { it.copy(state = UIState.LOADING) }
        viewModelScope.launch {
            when (val result = getPeopleUseCase()) {
                is RequestResult.Success -> _screenState.update {
                    it.copy(
                        state = UIState.DATA,
                        data = result.data
                    )
                }

                is RequestResult.Error -> _screenState.update {
                    it.copy(
                        state = UIState.ERROR,
                        error = result.text
                    )
                }
            }
        }
    }
}