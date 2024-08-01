package com.example.starwars.domain

import com.example.starwars.PersonByIdQuery
import com.example.starwars.data.repo.DataRepository
import com.example.starwars.domain.models.mapToPersonExtendedModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.given

@OptIn(ExperimentalCoroutinesApi::class)
class GetPersonUseCaseTest {

    private lateinit var getPersonUseCase: GetPersonUseCase
    private val repository: DataRepository = mock()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined) // Set main dispatcher for testing
        getPersonUseCase = GetPersonUseCase(repository)
    }

    @Test
    fun `invoke returns Success when repository returns Person`() = runTest {
        val personId = "1"
        val person = PersonByIdQuery.Person(
            id = "1",
            name = "Luke Skywalker",
            homeworld = PersonByIdQuery.Homeworld(
                id = "id",
                name = "Tatooine",
                population = 120000.00,
                gravity = "1 standard",
                orbitalPeriod = 12,
                rotationPeriod = 13,
                surfaceWater = 1.00,
            )
        )
        val expectedResult = RequestResult.Success(person.mapToPersonExtendedModel())

        given(repository.loadPersonById(personId)).willReturn(Result.success(person))
        val result = getPersonUseCase(personId)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `invoke returns Error when repository returns Exception`() = runTest {
        val personId = "1"
        val exception = Exception("Test exception")
        val expectedResult = RequestResult.Error(exception.message ?: "")

        given(repository.loadPersonById(personId)).willReturn(Result.failure(exception))
        val result = getPersonUseCase(personId)

        assertEquals(expectedResult, result)
    }
}