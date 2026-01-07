package com.liftechnology.planalimenticio.domain.usecase

import com.liftechnology.planalimenticio.data.local.entity.FoodEntity
import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Caso de uso para obtener todos los alimentos almacenados.
 * Retorna todos los alimentos del Sistema Mexicano de Alimentos y Equivalentes.
 * 
 * @param repository Repositorio local de alimentos
 * @param dispatcher Dispatcher de corrutinas (por defecto Dispatchers.Default)
 * 
 * @author pelkidev
 */
class GetAllFoodsUseCase(
    private val repository: FoodLocalRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    /**
     * Ejecuta el caso de uso y retorna un Flow con todos los alimentos.
     * 
     * @return Flow que emite una lista de todos los alimentos
     */
    operator fun invoke(): Flow<List<FoodEntity>> {
        return repository.getAllFoods()
            .flowOn(dispatcher)
    }

    /**
     * Ejecuta el caso de uso y retorna una lista suspendida con todos los alimentos.
     * 
     * @return Lista de todos los alimentos
     */
    suspend fun invokeSuspend(): List<FoodEntity> {
        return repository.getAllFoodsSuspend()
    }
}

