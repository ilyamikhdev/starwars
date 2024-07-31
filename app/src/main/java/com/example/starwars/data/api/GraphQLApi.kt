package com.example.starwars.data.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.starwars.AllPeopleQuery
import com.example.starwars.PersonDetailsByIdQuery

import javax.inject.Inject

class GraphQLApi @Inject constructor(
    private val apolloClient: ApolloClient
) {

    suspend fun getAllPeople() = apolloClient.query(AllPeopleQuery()).execute()

    suspend fun getPersonDetailsById(personId: String) =
        apolloClient.query(PersonDetailsByIdQuery(personId = Optional.present(personId))).execute()
}