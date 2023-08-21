package com.liftechnology.planalimenticio.data.network

import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import retrofit2.Response

class GetItemsUseCase(private val repository: ItemRepository) {
    suspend fun execute(): Response<PrincipalResponse> {
        return repository.getItems()
    }
}
