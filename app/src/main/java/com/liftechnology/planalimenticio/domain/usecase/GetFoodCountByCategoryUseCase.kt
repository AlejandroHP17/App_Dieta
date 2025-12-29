package com.liftechnology.planalimenticio.domain.usecase

import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Caso de uso para obtener el conteo de alimentos por categoría.
 * Útil para mostrar estadísticas o validar si una categoría tiene alimentos.
 * 
 * @param repository Repositorio local de alimentos
 * @param dispatcher Dispatcher de corrutinas (por defecto Dispatchers.Default)
 * 
 * @author pelkidev
 */
class GetFoodCountByCategoryUseCase(
    private val repository: FoodLocalRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    /**
     * Ejecuta el caso de uso y retorna el número de alimentos en la categoría.
     * 
     * @param category Nombre de la categoría
     * @return Número de alimentos en la categoría especificada
     */
    suspend operator fun invoke(category: String): Int {
        return withContext(dispatcher) {
            repository.getFoodCountByCategory(category)
        }
    }
}

