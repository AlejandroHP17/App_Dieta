package com.liftechnology.planalimenticio.domain.usecase

import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Caso de uso para obtener todas las categorías de alimentos disponibles.
 * Retorna las categorías del Sistema Mexicano de Alimentos y Equivalentes.
 * 
 * @param repository Repositorio local de alimentos
 * @param dispatcher Dispatcher de corrutinas (por defecto Dispatchers.Default)
 * 
 * @author pelkidev
 */
class GetAllCategoriesUseCase(
    private val repository: FoodLocalRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    /**
     * Ejecuta el caso de uso y retorna un Flow con todas las categorías.
     * 
     * @return Flow que emite una lista de nombres de categorías
     */
    operator fun invoke(): Flow<List<String>> {
        return repository.getAllCategories()
            .flowOn(dispatcher)
    }

    /**
     * Ejecuta el caso de uso y retorna una lista suspendida con todas las categorías.
     * 
     * @return Lista de nombres de categorías
     */
    suspend fun invokeSuspend(): List<String> {
        return repository.getAllCategoriesSuspend()
    }
}

