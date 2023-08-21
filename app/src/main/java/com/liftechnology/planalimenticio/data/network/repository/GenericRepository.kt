package com.liftechnology.planalimenticio.data.network.repository

import com.liftechnology.planalimenticio.data.network.models.response.GenericResponse
import com.liftechnology.planalimenticio.data.network.models.response.FoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiClient {

    /** Use the vegetable Api */
    @GET()
    suspend fun getVegetableApi(@Url url: String): Response<GenericResponse<FoodResponse>>


}