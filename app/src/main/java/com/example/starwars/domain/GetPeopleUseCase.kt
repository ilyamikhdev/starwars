package com.example.starwars.domain

import com.example.starwars.data.repo.DataRepository
import com.example.starwars.domain.models.PersonModel
import com.example.starwars.domain.models.mapToPersonModelList
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetPeopleUseCase @Inject constructor(
    private val repository: DataRepository
) {

    suspend operator fun invoke(): RequestResult<List<PersonModel>> {
        return withContext(Dispatchers.IO) {
            repository.loadAllPeople().fold(
                onSuccess = { value ->
                    RequestResult.Success(data = value.people.mapToPersonModelList())
                },
                onFailure = { exception ->
                    RequestResult.Error(exception.message ?: "")
                }
            )
        }
    }
}