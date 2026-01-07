package com.liftechnology.planalimenticio.domain.usecase

import com.liftechnology.planalimenticio.data.local.entity.FoodEntity
import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Caso de uso para buscar alimentos por nombre.
 * Realiza una búsqueda parcial (case-insensitive) en los nombres de los alimentos.
 * 
 * @param repository Repositorio local de alimentos
 * @param dispatcher Dispatcher de corrutinas (por defecto Dispatchers.Default)
 * 
 * @author pelkidev
 */
class SearchFoodsUseCase(
    private val repository: FoodLocalRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    /**
     * Ejecuta el caso de uso y retorna un Flow con los alimentos que coinciden con la búsqueda.
     * 
     * @param searchQuery Término de búsqueda (ej: "manzana", "pollo", etc.)
     * @return Flow que emite una lista de alimentos que coinciden con la búsqueda
     */
    operator fun invoke(searchQuery: String): Flow<List<FoodEntity>> {
        return repository.searchFoods(searchQuery)
            .flowOn(dispatcher)
    }

    /**
     * Ejecuta el caso de uso y retorna una lista suspendida con los alimentos que coinciden.
     * 
     * @param searchQuery Término de búsqueda
     * @return Lista de alimentos que coinciden con la búsqueda
     */
    suspend fun invokeSuspend(searchQuery: String): List<FoodEntity> {
        return repository.searchFoodsSuspend(searchQuery)
    }
}

