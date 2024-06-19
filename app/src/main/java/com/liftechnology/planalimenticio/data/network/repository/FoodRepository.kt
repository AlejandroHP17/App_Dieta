package com.liftechnology.planalimenticio.data.network.repository

import com.liftechnology.planalimenticio.data.local.dao.FoodDao
import com.liftechnology.planalimenticio.data.local.entity.FoodEntity
import com.liftechnology.planalimenticio.data.network.models.response.FoodResponse
import com.liftechnology.planalimenticio.data.network.service.ListFoodApi

interface FoodRepository {
    /** Repository para enlazar con el UseCase
     * @author pelkidev
     * @date 30/08/2023
     * @return [FoodResponse] modelo de este tipo
     * */
    suspend fun getListFood(url: String?): List<FoodResponse>
}


class FoodRepositoryImpl(
    private val apiListFood : ListFoodApi,
    private val foodDao: FoodDao
) : FoodRepository {
    /** Conexión para mandar a llamar el API
     * @author pelkidev
     * @date 30/08/2023
     * */
    override suspend fun getListFood(url: String?): List<FoodResponse> {
        
        /* Salida por el servicio */
        val response = apiListFood.callApi(url)
        if (response.isSuccessful) {
            val responseData = response.body()?.result
            if (responseData != null) {
                // Mapea la respuesta de la API a entidades de la base de datos local
                val foodEntities = responseData.map {
                    it.let { response ->
                        FoodEntity(
                            food = response.food,
                            suggestedQuantity = response.suggestedQuantity,
                            unit = response.unit,
                            netWeightG= response.netWeightG,
                            roundedGrossWeightG = response.roundedGrossWeightG,
                            energyKcal = response.energyKcal,
                            proteinG = response.proteinG,
                            lipidsG =response.lipidsG,
                            carbohydratesG =response.carbohydratesG,
                            fiverG =response.fiverG,
                            vitaminAUgRe =response.vitaminAUgRe,
                            ascorbicAcidMg = response.ascorbicAcidMg,
                            folicAcidUg = response.folicAcidUg,
                            ironNoHemMg = response.ironNoHemMg,
                            potassiumMg = response.potassiumMg,
                            hypoglycemicIndex = response.hypoglycemicIndex,
                            hypoglycemicLoad =response.hypoglycemicLoad,
                            sugarPerEquivalentG = response.sugarPerEquivalentG,
                            calciumMg= response.calciumMg,
                            ironMg =response.ironMg,
                            sodiumMg =response.sodiumMg,
                            cholesterolMg =response.cholesterolMg,
                            seleniumMg =response.seleniumMg,
                            seleniumUg =response.seleniumUg,
                            phosphorusMg =response.phosphorusMg,
                            agSaturatedG =response.agSaturatedG,
                            agMonounsaturatedG =response.agMonounsaturatedG,
                            agPolyunsaturatedG =response.agPolyunsaturatedG,
                            category = response.category
                        )
                    }
                }
                foodDao.insertAllListFood(foodEntities)
                return responseData
            }
        }
        // En caso de que no haya datos disponibles
        throw Exception("No data available")
    }
}