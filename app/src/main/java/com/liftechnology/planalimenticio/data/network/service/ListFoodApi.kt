package com.liftechnology.planalimenticio.data.network.service

import com.liftechnology.planalimenticio.data.network.models.response.FoodResponse
import com.liftechnology.planalimenticio.data.network.models.response.GenericResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ListFoodApi {
    /** Realiza la petición al API
     * @author pelkidev
     * @date 28/08/2023
     * @return [GenericResponse] modelo de este tipo
     * */
    @GET
    suspend fun callApi(@Url url: String) : Response<GenericResponse<FoodResponse>>
}