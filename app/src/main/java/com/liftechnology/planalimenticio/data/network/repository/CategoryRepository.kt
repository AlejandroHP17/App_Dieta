package com.liftechnology.planalimenticio.data.network.repository

import android.util.Log
import com.liftechnology.planalimenticio.data.local.dao.CategoryDao
import com.liftechnology.planalimenticio.data.local.entity.CategoryEntity
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.data.network.service.CategoryApi

interface CategoryRepository {
    /** Repository para enlazar con el UseCase
     * @author pelkidev
     * @date 28/08/2023
     * @return [CategoryResponse] modelo de este tipo
     * */
    suspend fun getCategory(): List<CategoryResponse?>?
}

class CategoryRepositoryImpl(
    private val apiCategory: CategoryApi,
    private val categoryDao: CategoryDao
) : CategoryRepository {
    /** Conexión para mandar a llamar el API
     * @author pelkidev
     * @date 28/08/2023
     * */
    override suspend fun getCategory(): List<CategoryResponse?> {

        /* Salida por el servicio */
        val response = apiCategory.callApi()
        if (response.isSuccessful) {
            val responseData = response.body()?.result
            if (responseData != null) {
                // Mapea la respuesta de la API a entidades de la base de datos local
                val categoryEntities = responseData.mapNotNull { it?.let { response ->
                    CategoryEntity(
                        category = response.category,
                        url = response.url,
                        startColor = response.startColor,
                        endColor = response.endColor
                    )
                }}
                // Guarda las entidades en la base de datos local
                categoryDao.insertAllCategory(categoryEntities)


                //localDataSource.saveCategory(responseData)
                return responseData
            }
        }
        // En caso de que no haya datos disponibles
        throw Exception("No data available")


    }
}
