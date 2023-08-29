package com.liftechnology.planalimenticio.data.network.repository

import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.data.network.service.MyApi

interface MainRepository {
    /** Repository para enlazar con el UseCase
     * @author pelkidev
     * @date 28/08/2023
     * @return [CategoryResponse] modelo de este tipo
     * */
    suspend fun getCategory(): List<CategoryResponse?>?
}

class MainRepositoryImpl(
    private val apiCategory: MyApi
) : MainRepository {
    /** Conexión para mandar a llamar el API
     * @author pelkidev
     * @date 28/08/2023
     * */
    override suspend fun getCategory(): List<CategoryResponse?> {

        // Se agrega validacion para firebase y leer base de datos local
        // Del mismo modo se agrega una base de datos local
        /*val localData = localDataSource.getCachedCategory()

        if (localData != null) {
            // Si hay datos en la base de datos local, devuelve esos datos
            return localData
        }*/

        /* Salida por el servicio */
        val response = apiCategory.callApi()
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
