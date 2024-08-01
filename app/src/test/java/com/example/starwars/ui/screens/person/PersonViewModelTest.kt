package com.example.starwars.ui.screens.person

import com.example.starwars.domain.RequestResult
import com.example.starwars.domain.models.HomeworldModel
import com.example.starwars.domain.models.PersonExtendedModel
import com.example.starwars.domain.usecases.GetPersonUseCase
import com.example.starwars.ui.common.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.given

class PersonViewModelTest {
    private val idMock = "id_mock"
    private val errorMessageMock = "Not found"

    private lateinit var getPersonUseCase: GetPersonUseCase
    private lateinit var viewModel: PersonViewModel


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        getPersonUseCase = mock()
        viewModel = PersonViewModel(getPersonUseCase)
    }

    @Test
    fun `Call getPersonUseCase - Success`() {
        runTest {
            val person = PersonExtendedModel(
                id = "1",
                name = "Luke Skywalker",
                homeworld = HomeworldModel(
                    id = "id",
                    name = "Tatooine",
                    population = 120000.00,
                    gravity = "1 standard",
                    orbitalPeriod = 12,
                    rotationPeriod = 13,
                    surfaceWater = 1.00,
                )
            )

            given(getPersonUseCase(idMock)).willReturn(RequestResult.Success(person))
            // When it is initialized
            viewModel.loadPerson(idMock)


            assert(viewModel.screenState.value.data == person)
            assert(viewModel.screenState.value.error == null)
            assert(viewModel.screenState.value.state == UIState.DATA)
        }
    }

    @Test
    fun `Call getPersonUseCase - Error`() {
        runTest {
            given(getPersonUseCase(idMock)).willReturn(RequestResult.Error(errorMessageMock))
            viewModel.loadPerson(idMock)
            assert(viewModel.screenState.value.data == null)
            assert(viewModel.screenState.value.error == errorMessageMock)
            assert(viewModel.screenState.value.state == UIState.ERROR)
        }
    }

    @Test
    fun `Call showDialog(true)`() {
        runTest {
            viewModel.showDialog(true)
            assert(viewModel.screenState.value.showDialog)
        }
    }

    @Test
    fun `oCall showDialog(false)`() {
        runTest {
            viewModel.showDialog(false)
            assert(!viewModel.screenState.value.showDialog)

        }
    }

}