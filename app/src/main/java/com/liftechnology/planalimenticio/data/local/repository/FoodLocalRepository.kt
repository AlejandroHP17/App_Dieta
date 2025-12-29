package com.liftechnology.planalimenticio.data.local.repository

import com.liftechnology.planalimenticio.data.local.dao.FoodDao
import com.liftechnology.planalimenticio.data.local.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio local para gestionar los datos de alimentos almacenados en Room.
 * Proporciona una capa de abstracción sobre el DAO y maneja las operaciones de datos.
 * 
 * @author pelkidev
 */
class FoodLocalRepository(private val foodDao: FoodDao) {

    /**
     * Obtiene todos los alimentos de una categoría específica como Flow.
     * Útil para observación reactiva en la UI.
     * 
     * @param category Nombre de la categoría
     * @return Flow que emite una lista de alimentos de la categoría
     */
    fun getFoodsByCategory(category: String): Flow<List<FoodEntity>> {
        return foodDao.getFoodsByCategory(category)
    }

    /**
     * Obtiene todos los alimentos de una categoría específica (versión suspendida).
     * 
     * @param category Nombre de la categoría
     * @return Lista de alimentos de la categoría
     */
    suspend fun getFoodsByCategorySuspend(category: String): List<FoodEntity> {
        return foodDao.getFoodsByCategorySuspend(category)
    }

    /**
     * Obtiene todos los alimentos almacenados como Flow.
     * 
     * @return Flow que emite una lista de todos los alimentos
     */
    fun getAllFoods(): Flow<List<FoodEntity>> {
        return foodDao.getAllFoods()
    }

    /**
     * Obtiene todos los alimentos (versión suspendida).
     * 
     * @return Lista de todos los alimentos
     */
    suspend fun getAllFoodsSuspend(): List<FoodEntity> {
        return foodDao.getAllFoodsSuspend()
    }

    /**
     * Busca alimentos por nombre como Flow.
     * 
     * @param searchQuery Término de búsqueda
     * @return Flow que emite una lista de alimentos que coinciden con la búsqueda
     */
    fun searchFoods(searchQuery: String): Flow<List<FoodEntity>> {
        return foodDao.searchFoods(searchQuery)
    }

    /**
     * Busca alimentos por nombre (versión suspendida).
     * 
     * @param searchQuery Término de búsqueda
     * @return Lista de alimentos que coinciden con la búsqueda
     */
    suspend fun searchFoodsSuspend(searchQuery: String): List<FoodEntity> {
        return foodDao.searchFoodsSuspend(searchQuery)
    }

    /**
     * Obtiene un alimento específico por su ID como Flow.
     * 
     * @param id ID del alimento
     * @return Flow que emite el alimento encontrado o null
     */
    fun getFoodById(id: Int): Flow<FoodEntity?> {
        return foodDao.getFoodById(id)
    }

    /**
     * Obtiene un alimento específico por su ID (versión suspendida).
     * 
     * @param id ID del alimento
     * @return El alimento encontrado o null
     */
    suspend fun getFoodByIdSuspend(id: Int): FoodEntity? {
        return foodDao.getFoodByIdSuspend(id)
    }

    /**
     * Obtiene todas las categorías únicas disponibles como Flow.
     * 
     * @return Flow que emite una lista de nombres de categorías
     */
    fun getAllCategories(): Flow<List<String>> {
        return foodDao.getAllCategories()
    }

    /**
     * Obtiene todas las categorías únicas (versión suspendida).
     * 
     * @return Lista de nombres de categorías
     */
    suspend fun getAllCategoriesSuspend(): List<String> {
        return foodDao.getAllCategoriesSuspend()
    }

    /**
     * Obtiene el conteo de alimentos por categoría.
     * 
     * @param category Nombre de la categoría
     * @return Número de alimentos en la categoría
     */
    suspend fun getFoodCountByCategory(category: String): Int {
        return foodDao.getFoodCountByCategory(category)
    }

    /**
     * Inserta una lista de alimentos en la base de datos.
     * 
     * @param foods Lista de alimentos a insertar
     */
    suspend fun insertAllFoods(foods: List<FoodEntity>) {
        foodDao.insertAllFoods(foods)
    }

    /**
     * Inserta un único alimento en la base de datos.
     * 
     * @param food Alimento a insertar
     */
    suspend fun insertFood(food: FoodEntity) {
        foodDao.insertFood(food)
    }

    /**
     * Elimina todos los alimentos de la base de datos.
     */
    suspend fun deleteAllFoods() {
        foodDao.deleteAllFoods()
    }

    /**
     * Elimina todos los alimentos de una categoría específica.
     * 
     * @param category Nombre de la categoría
     */
    suspend fun deleteFoodsByCategory(category: String) {
        foodDao.deleteFoodsByCategory(category)
    }

    /**
     * Método legacy para compatibilidad con código existente.
     * @deprecated Usar getFoodsByCategorySuspend en su lugar
     */
    @Deprecated("Usar getFoodsByCategorySuspend en su lugar", ReplaceWith("getFoodsByCategorySuspend(categoria)"))
    suspend fun getCategoryFood(categoria: String?): List<FoodEntity> {
        return if (categoria != null) {
            foodDao.getFoodsByCategorySuspend(categoria)
        } else {
            emptyList()
        }
    }
}