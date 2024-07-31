package com.example.starwars.data.repo

import com.example.starwars.AllPeopleQuery
import com.example.starwars.PersonByIdQuery
import com.example.starwars.data.api.GraphQLApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository  @Inject constructor(
    private val api: GraphQLApi,
) {

    suspend fun loadAllPeople(): Result<AllPeopleQuery.AllPeople> {
        return try {
            val response = api.getAllPeople()

            if (response.hasErrors()) {
                Result.failure(Exception(response.errors?.first()?.message))
            } else {
                val people = response.data?.allPeople
                if (people != null) {
                    Result.success(people)
                } else {
                    Result.failure(Exception("People are empty."))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loadPersonById(id: String): Result<PersonByIdQuery.Person> {
        return try {
            val response = api.getPersonDetailsById(id)

            if (response.hasErrors()) {
                Result.failure(Exception(response.errors?.first()?.message))
            } else {
                val person = response.data?.person
                if (person != null) {
                    Result.success(person)
                } else {
                    Result.failure(Exception("The person doesn't exist."))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}