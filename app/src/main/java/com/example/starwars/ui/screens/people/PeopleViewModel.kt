package com.example.starwars.ui.screens.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.domain.PeopleResult
import com.example.starwars.domain.PeopleUseCase
import com.example.starwars.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val useCase: PeopleUseCase
) : ViewModel() {
    private val _screenState = MutableStateFlow(PeopleState())
    val screenState: StateFlow<PeopleState> = _screenState

    init {
        viewModelScope.launch {
            _screenState.update { it.copy(state = UIState.LOADING) }
            when (val result = useCase()) {
                is PeopleResult.Error -> _screenState.update {
                    it.copy(
                        state = UIState.ERROR,
                        error = result.text
                    )
                }

                is PeopleResult.Success -> _screenState.update {
                    it.copy(
                        state = UIState.DATA,
                        data = result.items,
                    )
                }
            }
        }
    }
}