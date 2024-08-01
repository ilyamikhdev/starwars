package com.example.starwars.ui.screens.people

import com.example.starwars.domain.GetPeopleUseCase
import com.example.starwars.domain.RequestResult
import com.example.starwars.domain.models.PersonModel
import com.example.starwars.ui.common.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.given

internal class PeopleViewModelTest {

    private lateinit var getPeopleUseCase: GetPeopleUseCase
    private lateinit var viewModel: PeopleViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        getPeopleUseCase = mock()
    }

    @Test
    fun `Call getPeopleUseCase - Success`() {
        runTest {
            val people: List<PersonModel> = listOf(
                PersonModel(
                    id = "id_mock",
                    name = "Name Mock",
                    height = 100,
                    mass = 200.20,
                )
            )
            given(getPeopleUseCase()).willReturn(RequestResult.Success(people))
            // When it is initialized
            viewModel = PeopleViewModel(getPeopleUseCase)
            assert(viewModel.screenState.value.data == people)
            assert(viewModel.screenState.value.error == null)
            assert(viewModel.screenState.value.state == UIState.DATA)
        }
    }

    @Test
    fun `Call getPeopleUseCase - Error`() {
        runTest {
            val errorMessage = "Not found"
            given(getPeopleUseCase()).willReturn(RequestResult.Error(errorMessage))
            // When it is initialized
            viewModel = PeopleViewModel(getPeopleUseCase)
            assert(viewModel.screenState.value.data.isEmpty())
            assert(viewModel.screenState.value.error == errorMessage)
            assert(viewModel.screenState.value.state == UIState.ERROR)
        }
    }

}