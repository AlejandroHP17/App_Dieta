package com.liftechnology.planalimenticio.domain.usecase

import com.liftechnology.planalimenticio.data.local.entity.FoodEntity
import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Caso de uso para obtener un alimento específico por su ID.
 * 
 * @param repository Repositorio local de alimentos
 * @param dispatcher Dispatcher de corrutinas (por defecto Dispatchers.Default)
 * 
 * @author pelkidev
 */
class GetFoodByIdUseCase(
    private val repository: FoodLocalRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    /**
     * Ejecuta el caso de uso y retorna un Flow con el alimento encontrado.
     * 
     * @param id ID del alimento
     * @return Flow que emite el alimento encontrado o null si no existe
     */
    operator fun invoke(id: Int): Flow<FoodEntity?> {
        return repository.getFoodById(id)
            .flowOn(dispatcher)
    }

    /**
     * Ejecuta el caso de uso y retorna el alimento encontrado (versión suspendida).
     * 
     * @param id ID del alimento
     * @return El alimento encontrado o null si no existe
     */
    suspend fun invokeSuspend(id: Int): FoodEntity? {
        return repository.getFoodByIdSuspend(id)
    }
}

