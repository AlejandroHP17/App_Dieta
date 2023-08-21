package com.liftechnology.planalimenticio.data.network.repository

import com.liftechnology.planalimenticio.data.network.service.PrincipalApi
import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import retrofit2.Response

class PrincipalRepository (){
    /** Suspend function complete*/
    suspend fun getCategories(): Response<PrincipalResponse> {
        return PrincipalApi().getSubApis()
    }

}

