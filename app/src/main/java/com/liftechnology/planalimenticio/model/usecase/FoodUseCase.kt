package com.liftechnology.planalimenticio.model.usecase

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import com.liftechnology.planalimenticio.data.network.models.response.FoodResponse
import com.liftechnology.planalimenticio.data.network.repository.FoodRepository
import com.liftechnology.planalimenticio.ui.utils.ErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

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
                if (!localData.isNullOrEmpty()) {
                    val foodResponses = localData.map { foodEntity ->
                        FoodResponse(
                            foodEntity.alimento,
                            foodEntity.cantidad_sugerida,
                            foodEntity.unidad,
                            foodEntity.peso_neto_g,
                            foodEntity.peso_bruto_redondeado_g,
                            foodEntity.energia_kcal,
                            foodEntity.proteina_g,
                            foodEntity.lipidos_g,
                            foodEntity.hidratos_de_carbono_g,
                            foodEntity.fibra_g,
                            foodEntity.vitamina_a_ug_re,
                            foodEntity.acido_ascorbico_mg,
                            foodEntity.acido_folico_ug,
                            foodEntity.hierro_no_hem_mg,
                            foodEntity.potasio_mg,
                            foodEntity.indice_glicemico,
                            foodEntity.carga_glicemica,
                            foodEntity.azucar_por_equivalente_g,
                            foodEntity.calcio_mg,
                            foodEntity.hierro_mg,
                            foodEntity.sodio_mg,
                            foodEntity.colesterol_mg,
                            foodEntity.selenio_mg,
                            foodEntity.selenio_ug,
                            foodEntity.fosforo_mg,
                            foodEntity.ag_saturados_g,
                            foodEntity.ag_monoinsaturados_g,
                            foodEntity.ag_poliinsaturados_g,
                            foodEntity.categoria,
                        )
                    }
                    callback.invoke(foodResponses, null)
                } else {
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

