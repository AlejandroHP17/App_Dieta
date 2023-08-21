package com.liftechnology.planalimenticio.data.network

import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import com.liftechnology.planalimenticio.data.network.service.PrincipalApi
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getItems(): Response<PrincipalResponse>
}

class RemoteDataSourceImpl : RemoteDataSource {
    override suspend fun getItems(): Response<PrincipalResponse> {
            return PrincipalApi().getSubApis()

    }
}