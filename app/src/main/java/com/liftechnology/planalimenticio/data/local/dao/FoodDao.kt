package com.liftechnology.planalimenticio.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.liftechnology.planalimenticio.data.local.entity.FoodEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) para la tabla de alimentos.
 * Proporciona métodos para acceder a los datos de alimentos almacenados en la base de datos Room.
 * 
 * @author pelkidev
 */
@Dao
interface FoodDao {

    /**
     * Obtiene todos los alimentos de una categoría específica.
     * 
     * @param category Nombre de la categoría (ej: "VERDURAS", "FRUTAS", etc.)
     * @return Flow que emite una lista de alimentos de la categoría especificada
     */
    @Query("SELECT * FROM food_table WHERE category = :category ORDER BY food ASC")
    fun getFoodsByCategory(category: String): Flow<List<FoodEntity>>

    /**
     * Obtiene todos los alimentos de una categoría específica (versión suspendida).
     * 
     * @param category Nombre de la categoría
     * @return Lista de alimentos de la categoría especificada
     */
    @Query("SELECT * FROM food_table WHERE category = :category ORDER BY food ASC")
    suspend fun getFoodsByCategorySuspend(category: String): List<FoodEntity>

    /**
     * Obtiene todos los alimentos almacenados en la base de datos.
     * 
     * @return Flow que emite una lista de todos los alimentos
     */
    @Query("SELECT * FROM food_table ORDER BY category ASC, food ASC")
    fun getAllFoods(): Flow<List<FoodEntity>>

    /**
     * Obtiene todos los alimentos (versión suspendida).
     * 
     * @return Lista de todos los alimentos
     */
    @Query("SELECT * FROM food_table ORDER BY category ASC, food ASC")
    suspend fun getAllFoodsSuspend(): List<FoodEntity>

    /**
     * Busca alimentos por nombre (búsqueda parcial, case-insensitive).
     * 
     * @param searchQuery Término de búsqueda
     * @return Flow que emite una lista de alimentos que coinciden con la búsqueda
     */
    @Query("SELECT * FROM food_table WHERE food LIKE '%' || :searchQuery || '%' COLLATE NOCASE ORDER BY food ASC")
    fun searchFoods(searchQuery: String): Flow<List<FoodEntity>>

    /**
     * Busca alimentos por nombre (versión suspendida).
     * 
     * @param searchQuery Término de búsqueda
     * @return Lista de alimentos que coinciden con la búsqueda
     */
    @Query("SELECT * FROM food_table WHERE food LIKE '%' || :searchQuery || '%' COLLATE NOCASE ORDER BY food ASC")
    suspend fun searchFoodsSuspend(searchQuery: String): List<FoodEntity>

    /**
     * Obtiene un alimento específico por su ID.
     * 
     * @param id ID del alimento
     * @return Flow que emite el alimento encontrado o null
     */
    @Query("SELECT * FROM food_table WHERE id = :id")
    fun getFoodById(id: Int): Flow<FoodEntity?>

    /**
     * Obtiene un alimento específico por su ID (versión suspendida).
     * 
     * @param id ID del alimento
     * @return El alimento encontrado o null
     */
    @Query("SELECT * FROM food_table WHERE id = :id")
    suspend fun getFoodByIdSuspend(id: Int): FoodEntity?

    /**
     * Obtiene todas las categorías únicas disponibles.
     * 
     * @return Flow que emite una lista de nombres de categorías
     */
    @Query("SELECT DISTINCT category FROM food_table ORDER BY category ASC")
    fun getAllCategories(): Flow<List<String>>

    /**
     * Obtiene todas las categorías únicas (versión suspendida).
     * 
     * @return Lista de nombres de categorías
     */
    @Query("SELECT DISTINCT category FROM food_table ORDER BY category ASC")
    suspend fun getAllCategoriesSuspend(): List<String>

    /**
     * Obtiene el conteo de alimentos por categoría.
     * 
     * @param category Nombre de la categoría
     * @return Número de alimentos en la categoría
     */
    @Query("SELECT COUNT(*) FROM food_table WHERE category = :category")
    suspend fun getFoodCountByCategory(category: String): Int

    /**
     * Inserta una lista de alimentos en la base de datos.
     * Si un alimento ya existe (mismo nombre y categoría), se reemplaza.
     * 
     * @param foods Lista de alimentos a insertar
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFoods(foods: List<FoodEntity>)

    /**
     * Inserta un único alimento en la base de datos.
     * 
     * @param food Alimento a insertar
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodEntity)

    /**
     * Elimina todos los alimentos de la base de datos.
     */
    @Query("DELETE FROM food_table")
    suspend fun deleteAllFoods()

    /**
     * Elimina todos los alimentos de una categoría específica.
     * 
     * @param category Nombre de la categoría
     */
    @Query("DELETE FROM food_table WHERE category = :category")
    suspend fun deleteFoodsByCategory(category: String)
}