package com.example.starwars.domain

import com.example.starwars.data.repo.DataRepository
import com.example.starwars.domain.models.PersonExtendedModel
import com.example.starwars.domain.models.mapToPersonExtendedModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class PersonUseCase @Inject constructor(
    private val repository: DataRepository
) {

    suspend operator fun invoke(id: String): RequestResult<PersonExtendedModel> {
        return withContext(Dispatchers.IO) {
            repository.loadPersonById(id).fold(
                onSuccess = { value ->
                    RequestResult.Success(data = value.mapToPersonExtendedModel())
                },
                onFailure = { exception ->
                    RequestResult.Error(exception.message ?: "")
                }
            )
        }
    }
}