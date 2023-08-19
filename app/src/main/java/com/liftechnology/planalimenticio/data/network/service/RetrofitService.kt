package com.liftechnology.planalimenticio.data.network.service

import com.liftechnology.planalimenticio.data.network.enviroment.Environment
import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


/** Principal Api*/
interface PrincipalApi {

    // Catch the end point
    @GET(Environment.END_POINT)
    suspend fun getSubApis(): Response<PrincipalResponse>


    // Build the retrofit, is generic
    companion object{
        operator fun invoke():PrincipalApi{
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Environment.URL_BASE)
                .build()
                .create(PrincipalApi::class.java)
        }
    }
}