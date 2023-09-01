package com.liftechnology.planalimenticio.data.network.repository

import android.util.Log
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
    suspend fun getListFood(url: String): List<FoodResponse>
}


class FoodRepositoryImpl(
    private val apiListFood : ListFoodApi,
    private val foodDao: FoodDao
) : FoodRepository {
    /** Conexión para mandar a llamar el API
     * @author pelkidev
     * @date 30/08/2023
     * */
    override suspend fun getListFood(url: String): List<FoodResponse> {
        
        /* Salida por el servicio */
        val response = apiListFood.callApi(url)
        if (response.isSuccessful) {
            val responseData = response.body()?.result
            if (responseData != null) {
                // Mapea la respuesta de la API a entidades de la base de datos local
                val foodEntities = responseData.mapNotNull {
                    it.let { response ->
                        FoodEntity(
                            alimento = response.alimento,
                            cantidad_sugerida = response.cantidad_sugerida,
                            unidad = response.unidad,
                            peso_neto_g= response.peso_neto_g,
                            peso_bruto_redondeado_g = response.peso_bruto_redondeado_g,
                            energia_kcal = response.energia_kcal,
                            proteina_g = response.proteina_g,
                            lipidos_g =response.lipidos_g,
                            hidratos_de_carbono_g =response.hidratos_de_carbono_g,
                            fibra_g =response.fibra_g,
                            vitamina_a_ug_re =response.vitamina_a_ug_re,
                            acido_ascorbico_mg = response.acido_ascorbico_mg,
                            acido_folico_ug = response.acido_folico_ug,
                            hierro_no_hem_mg = response.hierro_no_hem_mg,
                            potasio_mg = response.potasio_mg,
                            indice_glicemico = response.indice_glicemico,
                            carga_glicemica =response.carga_glicemica,
                            azucar_por_equivalente_g = response.azucar_por_equivalente_g,
                            calcio_mg= response.calcio_mg,
                            hierro_mg =response.hierro_mg,
                            sodio_mg =response.sodio_mg,
                            colesterol_mg =response.colesterol_mg,
                            selenio_mg =response.selenio_mg,
                            selenio_ug =response.selenio_ug,
                            fosforo_mg =response.fosforo_mg,
                            ag_saturados_g =response.ag_saturados_g,
                            ag_monoinsaturados_g =response.ag_monoinsaturados_g,
                            ag_poliinsaturados_g =response.ag_poliinsaturados_g,
                            categoria = response.categoria
                        )
                    }
                }
                foodDao.insertAllListFood(foodEntities)
                //localDataSource.saveCategory(responseData)
                return responseData
            }
        }
        // En caso de que no haya datos disponibles
        throw Exception("No data available")

    }
}