package com.liftechnology.planalimenticio.data.local.database

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liftechnology.planalimenticio.data.local.entity.FoodEntity
import com.liftechnology.planalimenticio.data.local.model.FoodJsonResponse
import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import com.liftechnology.planalimenticio.main.utils.Constants.NAME_JSON
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream

/**
 * Inicializador de la base de datos Room.
 * Se encarga de poblar la base de datos con los datos iniciales del Sistema Mexicano
 * de Alimentos y Equivalentes (SMAE) la primera vez que se ejecuta la aplicación.
 * 
 * @param context Contexto de la aplicación
 * @param repository Repositorio local de alimentos
 * 
 * @author pelkidev
 */
class DatabaseInitializer(
    private val context: Context,
    private val repository: FoodLocalRepository
) {
    private val gson = Gson()
    private val TAG = "DatabaseInitializer"

    /**
     * Inicializa la base de datos si está vacía.
     * Lee el archivo JSON de assets y lo inserta en la base de datos.
     * 
     * @return true si se inicializó la base de datos, false si ya estaba poblada
     */
    suspend fun initializeIfNeeded(): Boolean = withContext(Dispatchers.IO) {
        try {
            // Verifica si la base de datos ya está poblada
            val existingFoods = repository.getAllFoodsSuspend()
            if (existingFoods.isNotEmpty()) {
                Log.d(TAG, "Base de datos ya está inicializada con ${existingFoods.size} alimentos")
                return@withContext false // Ya está inicializada
            }

            // Lee el archivo JSON de assets
            val jsonString = loadJSONFromAssets()
            if (jsonString == null) {
                Log.e(TAG, "No se encontró el archivo alimentos_smae.json en assets")
                // Si no existe el archivo JSON, usa los datos hardcodeados
                val initialFoods = getInitialFoodsData()
                repository.insertAllFoods(initialFoods)
                Log.d(TAG, "Base de datos inicializada con ${initialFoods.size} alimentos hardcodeados")
                return@withContext true
            }

            // Parsea el JSON a una lista de FoodJsonResponse
            val type = object : TypeToken<List<FoodJsonResponse>>() {}.type
            val jsonResponses: List<FoodJsonResponse> = gson.fromJson(jsonString, type)
            
            Log.d(TAG, "JSON parseado correctamente: ${jsonResponses.size} alimentos encontrados")

            // Convierte los modelos JSON a FoodEntity
            val foods: List<FoodEntity> = jsonResponses.map { it.toFoodEntity() }

            // Inserta los alimentos en la base de datos
            repository.insertAllFoods(foods)
            Log.d(TAG, "✅ Base de datos inicializada correctamente con ${foods.size} alimentos del SMAE")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error al inicializar la base de datos", e)
            e.printStackTrace()
            // En caso de error, intenta con datos hardcodeados
            try {
                val initialFoods = getInitialFoodsData()
                repository.insertAllFoods(initialFoods)
                Log.d(TAG, "Base de datos inicializada con datos hardcodeados como fallback")
                true
            } catch (ex: Exception) {
                Log.e(TAG, "Error crítico al inicializar con datos hardcodeados", ex)
                ex.printStackTrace()
                false
            }
        }
    }

    /**
     * Lee un archivo JSON desde la carpeta assets.
     *
     * @return Contenido del archivo como String o null si no existe
     */
    private fun loadJSONFromAssets(): String? {
        return try {
            val inputStream: InputStream = context.assets.open(NAME_JSON)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            Log.e(TAG, "Error al leer archivo desde assets: $NAME_JSON", ex)
            null
        }
    }

    /**
     * Retorna los datos iniciales hardcodeados del SMAE.
     * Estos datos se usan como fallback si no existe el archivo JSON o hay un error.
     * 
     * @return Lista de alimentos iniciales
     */
    private fun getInitialFoodsData(): List<FoodEntity> {
        return listOf(
            // Ejemplo mínimo para fallback
            FoodEntity(
                id = 0,
                category = "VERDURAS",
                idCategory = 17, // ID para Verduras
                food = "Acelga cruda",
                suggestedQuantity = 2f,
                unit = "taza",
                netWeightG = "98",
                roundedGrossWeightG = 120,
                energyKcal = 22,
                proteinG = 2.2f,
                lipidsG = 0.1f,
                carbohydratesG = 4.3f,
                fiverG = 3.6f,
                vitaminAUgRe = 310.9f,
                ascorbicAcidMg = 29.5f,
                folicAcidUg = 14.8f,
                ironNoHemMg = 2.5f,
                potassiumMg = 749.8f,
                hypoglycemicIndex = 64.0f,
                hypoglycemicLoad = 2.7f,
                sugarPerEquivalentG = null,
                calciumMg = null,
                ironMg = null,
                sodiumMg = null,
                cholesterolMg = null,
                seleniumMg = null,
                phosphorusMg = null,
                agSaturatedG = null,
                agMonounsaturatedG = null,
                agPolyunsaturatedG = null
            )
        )
    }
}