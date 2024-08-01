package com.example.starwars.data.repo

import com.apollographql.apollo.api.ApolloResponse.Builder
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Optional
import com.benasher44.uuid.Uuid
import com.example.starwars.AllPeopleQuery
import com.example.starwars.PersonByIdQuery
import com.example.starwars.data.api.GraphQLApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.given

class DataRepositoryTest {

    private lateinit var repository: DataRepository
    private val api: GraphQLApi = mock()

    @Before
    fun setup() {
        repository = DataRepository(api)
    }

    @Test
    fun `loadAllPeople returns Success when API response is successful`() = runTest {
        val peopleResponse = Builder(
            operation = AllPeopleQuery(),
            requestUuid = Uuid.randomUUID()
        ).data(
            data = AllPeopleQuery.Data(
                allPeople = AllPeopleQuery.AllPeople(
                    people = listOf()
                )
            )
        ).build()

        given(api.getAllPeople()).willReturn(peopleResponse)

        val result = repository.loadAllPeople()

        assertEquals(Result.success(peopleResponse.data?.allPeople), result)
    }

    @Test
    fun `loadAllPeople returns Failure when API response is successful with NULL data`() = runTest {
        val peopleResponse = Builder(
            operation = AllPeopleQuery(),
            requestUuid = Uuid.randomUUID()
        ).data(
            data = null
        ).build()

        given(api.getAllPeople()).willReturn(peopleResponse)

        val result = repository.loadAllPeople()
        assert(result.isFailure)
        assertEquals("People are empty.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `loadAllPeople returns Failure when API response has errors`() = runTest {
        val errorResponse = Builder(
            operation = AllPeopleQuery(),
            requestUuid = Uuid.randomUUID()
        ).data(
            data = null // Simulate an error response
        )
            .errors(
                listOf(
                    Error.Builder(message = "Test error").build()
                )
            ).build()

        given(api.getAllPeople()).willReturn(errorResponse)

        val result = repository.loadAllPeople()
        assert(result.isFailure)
        assertEquals("Test error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `loadPersonById returns Success when API response is successful`() = runTest {
        val personId = "1"
        val personResponse = Builder(
            operation = PersonByIdQuery(Optional.present(personId)),
            requestUuid = Uuid.randomUUID()
        ).data(
            data = PersonByIdQuery.Data(
                person = PersonByIdQuery.Person(id = "id", name = "Luke Skywalker", homeworld = null)
            )
        ).build()

        given(api.getPersonDetailsById(personId)).willReturn(personResponse)

        val result = repository.loadPersonById(personId)

        assertEquals(Result.success(personResponse.data?.person), result)
    }

    @Test
    fun `loadPersonById returns Failure when API response is successful with NULL data`() = runTest {
        val personId = "1"
        val personResponse = Builder(
            operation = PersonByIdQuery(Optional.present(personId)),
            requestUuid = Uuid.randomUUID()
        ).data(
            data = null
        ).build()

        given(api.getPersonDetailsById(personId)).willReturn(personResponse)

        val result = repository.loadPersonById(personId)

        assert(result.isFailure)
        assertEquals("The person doesn't exist.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `loadPersonById returns Failure when API response has errors`() = runTest {
        val personId = "1"
        val errorResponse = Builder(
            operation = PersonByIdQuery(Optional.present(personId)),
            requestUuid = Uuid.randomUUID()
        ).data(
            data = null // Simulate an error response
        ).errors(
            listOf(
                Error.Builder(message = "Test error").build()
            )
        ).build()

        given(api.getPersonDetailsById(personId)).willReturn(errorResponse)

        val result = repository.loadPersonById(personId)

        assert(result.isFailure)
        assertEquals("Test error", result.exceptionOrNull()?.message)
    }
}