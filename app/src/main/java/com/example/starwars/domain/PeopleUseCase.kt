package com.example.starwars.domain

import com.example.starwars.data.repo.DataRepository
import com.example.starwars.domain.models.mapToPersonModelList
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class PeopleUseCase @Inject constructor(
    private val repository: DataRepository
) {

    suspend operator fun invoke(): PeopleResult {
        return withContext(Dispatchers.IO) {
            repository.loadAllPeople().fold(
                onSuccess = { value ->
                    PeopleResult.Success(items = value.people.mapToPersonModelList())
                },
                onFailure = { exception ->
                    PeopleResult.Error(exception.message ?: "")
                }
            )
        }
    }
}