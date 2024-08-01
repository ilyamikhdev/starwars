package com.example.starwars.data.repo

import com.example.starwars.AllPeopleQuery
import com.example.starwars.PersonByIdQuery
import com.example.starwars.data.api.GraphQLApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val api: GraphQLApi,
) {

    suspend fun loadAllPeople(): Result<AllPeopleQuery.AllPeople> {
        return try {
            api.getAllPeople().let { response ->
                if (response.hasErrors()) {
                    Result.failure(Exception(response.errors?.first()?.message))
                } else {
                    response.data?.allPeople?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("People are empty."))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loadPersonById(id: String): Result<PersonByIdQuery.Person> {
        return try {
            api.getPersonDetailsById(id).let { response ->
                if (response.hasErrors()) {
                    Result.failure(Exception(response.errors?.first()?.message))
                } else {
                    response.data?.person?.let {
                        Result.success(it)
                    } ?: Result.failure(Exception("The person doesn't exist."))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}