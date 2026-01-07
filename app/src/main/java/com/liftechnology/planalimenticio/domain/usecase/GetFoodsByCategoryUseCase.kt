package com.liftechnology.planalimenticio.domain.usecase

import com.liftechnology.planalimenticio.data.local.entity.FoodEntity
import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Caso de uso para obtener alimentos por categoría.
 * Obtiene todos los alimentos de una categoría específica del Sistema Mexicano de Alimentos y Equivalentes.
 * 
 * @param repository Repositorio local de alimentos
 * @param dispatcher Dispatcher de corrutinas (por defecto Dispatchers.Default)
 * 
 * @author pelkidev
 */
class GetFoodsByCategoryUseCase(
    private val repository: FoodLocalRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    /**
     * Ejecuta el caso de uso y retorna un Flow con los alimentos de la categoría.
     * 
     * @param category Nombre de la categoría (ej: "VERDURAS", "FRUTAS", "CEREALES SIN GRASA", etc.)
     * @return Flow que emite una lista de alimentos de la categoría especificada
     */
    operator fun invoke(category: String): Flow<List<FoodEntity>> {
        return repository.getFoodsByCategory(category)
            .flowOn(dispatcher)
    }

    /**
     * Ejecuta el caso de uso y retorna una lista suspendida con los alimentos de la categoría.
     * 
     * @param category Nombre de la categoría
     * @return Lista de alimentos de la categoría especificada
     */
    suspend fun invokeSuspend(category: String): List<FoodEntity> {
        return repository.getFoodsByCategorySuspend(category)
    }
}

