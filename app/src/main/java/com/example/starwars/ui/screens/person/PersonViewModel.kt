package com.example.starwars.ui.screens.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.domain.PersonUseCase
import com.example.starwars.domain.RequestResult
import com.example.starwars.ui.common.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val useCase: PersonUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(PersonState())
    val screenState: StateFlow<PersonState> = _screenState

    fun loadPerson(id: String) {
        _screenState.update { it.copy(state = UIState.LOADING) }
        viewModelScope.launch {
            when (val result = useCase.invoke(id)) {
                is RequestResult.Success -> _screenState.update {
                    it.copy(
                        state = UIState.DATA,
                        data = result.data,
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

    fun onShowDialog(show: Boolean) {
        _screenState.update { it.copy(showDialog = show) }
    }

}