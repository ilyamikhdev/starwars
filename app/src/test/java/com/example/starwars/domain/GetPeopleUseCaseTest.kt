package com.example.starwars.domain


import com.example.starwars.AllPeopleQuery
import com.example.starwars.data.repo.DataRepository
import com.example.starwars.domain.models.mapToPersonModelList
import com.example.starwars.domain.usecases.GetPeopleUseCase
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
class GetPeopleUseCaseTest {

    private lateinit var getPeopleUseCase: GetPeopleUseCase
    private val repository: DataRepository = mock()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined) // Set main dispatcher for testing
        getPeopleUseCase = GetPeopleUseCase(repository)
    }

    @Test
    fun `invoke returns Success when repository returns people`() = runTest {
        val people = AllPeopleQuery.AllPeople(
            people = listOf(
                AllPeopleQuery.Person(
                    id = "1",
                    name = "Luke Skywalker",
                    height = 172,
                    mass = 77.0
                ),
                AllPeopleQuery.Person(
                    id = "1",
                    name = null,
                    height = null,
                    mass = null
                )
            )
        )
        val expectedResult = RequestResult.Success(people.people.mapToPersonModelList())

        given(repository.loadAllPeople()).willReturn(Result.success(people))

        val result = getPeopleUseCase.invoke()

        assertEquals(expectedResult, result)
    }

    @Test
    fun `invoke returns Error when repository returns Exception`() = runTest {
        val exception = Exception("Test exception")
        val expectedResult = RequestResult.Error(exception.message ?: "")

        given(repository.loadAllPeople()).willReturn(Result.failure(exception))

        val result = getPeopleUseCase.invoke()

        assertEquals(expectedResult, result)
    }
}