package com.liftechnology.planalimenticio.data.network.repository

import com.liftechnology.planalimenticio.data.network.models.response.FoodResponse
import com.liftechnology.planalimenticio.data.network.service.ListFoodApi

interface ListFoodRepository {
    /** Repository para enlazar con el UseCase
     * @author pelkidev
     * @date 30/08/2023
     * @return [FoodResponse] modelo de este tipo
     * */
    suspend fun getListFood(url: String): List<FoodResponse>
}


class ListFoodRepositoryImpl(
    private val apiListFood : ListFoodApi
) : ListFoodRepository {
    /** Conexión para mandar a llamar el API
     * @author pelkidev
     * @date 30/08/2023
     * */
    override suspend fun getListFood(url: String): List<FoodResponse> {

        // Se agrega validacion para firebase y leer base de datos local
        // Del mismo modo se agrega una base de datos local
        /*val localData = localDataSource.getCachedCategory()

        if (localData != null) {
            // Si hay datos en la base de datos local, devuelve esos datos
            return localData
        }*/
        /* Salida por el servicio */
        val response = apiListFood.callApi(url)
        if (response.isSuccessful) {
            val responseData = response.body()?.result
            if (responseData != null) {
                //localDataSource.saveCategory(responseData)
                return responseData
            }
        }
        // En caso de que no haya datos disponibles
        throw Exception("No data available")

    }
}