package com.liftechnology.planalimenticio.data.network.service

import com.liftechnology.planalimenticio.data.network.models.response.GenericResponse
import com.liftechnology.planalimenticio.data.network.models.response.FoodResponse
import com.liftechnology.planalimenticio.data.network.repository.ApiClient
import com.liftechnology.planalimenticio.data.network.retrofit.RetrofitHelper
import com.liftechnology.planalimenticio.ui.utils.ErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SecondaryRetrofitService {

    // Variable of retrofit, is generic
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getItemsVegetable(
        next: String,
        callback: (success: GenericResponse<FoodResponse>?, error: String?) -> Unit
    ) {
        return withContext(Dispatchers.IO) {
            // Response of service from people
            val response = retrofit.create(ApiClient::class.java).getVegetableApi(url = next)
            if (response.isSuccessful) {
                // Ask for the body of the response
                if (response.body() != null) {
                    // Return the response with the callback; body
                    callback.invoke(response.body(), null)
                } else {
                    // Return the response with the callback; error for the app
                    callback.invoke(null, ErrorCode.ERROR_SERVICE_VEGETABLE)
                }
            } else {
                // Return the response with the callback; error for the service
                callback.invoke(null, ErrorCode.ERROR_SERVICE)
            }
        }
    }
}