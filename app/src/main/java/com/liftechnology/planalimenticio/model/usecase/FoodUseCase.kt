package com.liftechnology.planalimenticio.model.usecase

import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import com.liftechnology.planalimenticio.data.network.models.response.FoodResponse
import com.liftechnology.planalimenticio.data.network.repository.FoodRepository
import com.liftechnology.planalimenticio.ui.utils.ErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodUseCase(
    private val repository: FoodRepository,
    private val localRepository: FoodLocalRepository
) {

    /** Obtiene el listado de categorias y procesa la informacion para el viewmodel
     * @author pelkidev
     * @date 30/08/2023
     * */
    suspend fun getListFood(
        url: String,
        nameCategory : String,
        callback: (success: List<FoodResponse>?, error: String?) -> Unit
    ) {
        return withContext(Dispatchers.IO) {
            try {
                // Obtiene datos de la base de datos local
                val localData = localRepository.getCategoryFood(nameCategory)

                if (localData.isNotEmpty()) {
                    val foodResponses = localData.map { foodEntity ->
                        FoodResponse(
                            foodEntity.food,
                            foodEntity.suggestedQuantity,
                            foodEntity.unit,
                            foodEntity.netWeightG,
                            foodEntity.roundedGrossWeightG,
                            foodEntity.energyKcal,
                            foodEntity.proteinG,
                            foodEntity.lipidsG,
                            foodEntity.carbohydratesG,
                            foodEntity.fiverG,
                            foodEntity.vitaminAUgRe,
                            foodEntity.ascorbicAcidMg,
                            foodEntity.folicAcidUg,
                            foodEntity.ironNoHemMg,
                            foodEntity.potassiumMg,
                            foodEntity.hypoglycemicIndex,
                            foodEntity.hypoglycemicLoad,
                            foodEntity.sugarPerEquivalentG,
                            foodEntity.calciumMg,
                            foodEntity.ironMg,
                            foodEntity.sodiumMg,
                            foodEntity.cholesterolMg,
                            foodEntity.seleniumMg,
                            foodEntity.seleniumUg,
                            foodEntity.phosphorusMg,
                            foodEntity.agSaturatedG,
                            foodEntity.agMonounsaturatedG,
                            foodEntity.agPolyunsaturatedG,
                            foodEntity.category,
                        )
                    }
                    callback.invoke(foodResponses, null)
                }
                else {
                    val response = repository.getListFood(url)
                    if (response != null) {
                        // Si la respuesta no es nula, se considera exitosa y se invoca el callback con los datos
                        callback.invoke(response, null)
                    } else {
                        // Si la respuesta es nula, se considera un error en el servicio
                        callback.invoke(null, ErrorCode.ERROR_SERVICE)
                    }
                }
            } catch (e: Exception) {
                // En caso de una excepción, se invoca el callback con el mensaje de error
                callback.invoke(null, e.message)
            }
        }
    }
}

