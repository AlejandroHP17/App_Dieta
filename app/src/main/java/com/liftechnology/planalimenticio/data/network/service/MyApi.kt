package com.liftechnology.planalimenticio.data.network.service

import com.liftechnology.planalimenticio.data.network.enviroment.Environment
import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {
    /** Realiza la petición al API
     * @author pelkidev
     * @date 28/08/2023
     * @return [PrincipalResponse] modelo de este tipo
     * */
    @GET(Environment.END_POINT)
    suspend fun callApi() : Response<PrincipalResponse?>
}