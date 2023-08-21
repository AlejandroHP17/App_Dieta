package com.liftechnology.planalimenticio.data.network

import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import retrofit2.Response

class ItemRepository(private val remoteDataSource: RemoteDataSource) {
    suspend fun getItems(): Response<PrincipalResponse> {
        return remoteDataSource.getItems()
    }
}