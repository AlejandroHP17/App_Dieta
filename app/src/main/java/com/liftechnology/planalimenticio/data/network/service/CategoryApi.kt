package com.liftechnology.planalimenticio.data.network.service

import com.liftechnology.planalimenticio.data.network.enviroment.Environment
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.data.network.models.response.GenericResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApi {
    /** Realiza la petición al API
     * @author pelkidev
     * @date 28/08/2023
     * @return [GenericResponse] modelo de este tipo
     * */
    @GET(Environment.END_POINT)
    suspend fun callApi() : Response<GenericResponse<CategoryResponse>?>
}