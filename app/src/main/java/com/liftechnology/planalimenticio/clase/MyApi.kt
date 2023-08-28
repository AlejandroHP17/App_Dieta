package com.liftechnology.planalimenticio.clase

import com.liftechnology.planalimenticio.data.network.enviroment.Environment
import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {

    @GET(Environment.END_POINT)
    suspend fun callApi() : Response<PrincipalResponse>
}