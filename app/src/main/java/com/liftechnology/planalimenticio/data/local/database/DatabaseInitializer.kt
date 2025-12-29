package com.liftechnology.planalimenticio.data.local.database

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liftechnology.planalimenticio.data.local.entity.FoodEntity
import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
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
                return@withContext false // Ya está inicializada
            }

            // Lee el archivo JSON de assets
            val jsonString = loadJSONFromAssets("alimentos_smae.json")
            if (jsonString == null) {
                // Si no existe el archivo JSON, usa los datos hardcodeados
                val initialFoods = getInitialFoodsData()
                repository.insertAllFoods(initialFoods)
                return@withContext true
            }

            // Parsea el JSON a una lista de FoodEntity
            val type = object : TypeToken<List<FoodEntity>>() {}.type
            val foods: List<FoodEntity> = gson.fromJson(jsonString, type)

            // Inserta los alimentos en la base de datos
            repository.insertAllFoods(foods)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            // En caso de error, intenta con datos hardcodeados
            try {
                val initialFoods = getInitialFoodsData()
                repository.insertAllFoods(initialFoods)
                true
            } catch (ex: Exception) {
                ex.printStackTrace()
                false
            }
        }
    }

    /**
     * Lee un archivo JSON desde la carpeta assets.
     * 
     * @param fileName Nombre del archivo JSON
     * @return Contenido del archivo como String o null si no existe
     */
    private fun loadJSONFromAssets(fileName: String): String? {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    /**
     * Retorna los datos iniciales hardcodeados del SMAE.
     * Estos datos se usan como fallback si no existe el archivo JSON.
     * 
     * NOTA: Este método contiene una muestra de datos. Para una implementación completa,
     * deberías crear un archivo JSON con todos los datos del PDF o usar un parser de PDF.
     * 
     * @return Lista de alimentos iniciales
     */
    private fun getInitialFoodsData(): List<FoodEntity> {
        return listOf(
            // Ejemplo de VERDURAS
            FoodEntity(
                id = 0,
                category = "VERDURAS",
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
                seleniumUg = null,
                phosphorusMg = null,
                agSaturatedG = null,
                agMonounsaturatedG = null,
                agPolyunsaturatedG = null
            ),
            FoodEntity(
                id = 0,
                category = "VERDURAS",
                food = "Acelga picada cocida",
                suggestedQuantity = 0.5f,
                unit = "taza",
                netWeightG = "72",
                roundedGrossWeightG = 72,
                energyKcal = 19,
                proteinG = 1.9f,
                lipidsG = 0.1f,
                carbohydratesG = 3.9f,
                fiverG = 2.1f,
                vitaminAUgRe = 275.8f,
                ascorbicAcidMg = 17.9f,
                folicAcidUg = 10.1f,
                ironNoHemMg = 1.4f,
                potassiumMg = 654.5f,
                hypoglycemicIndex = 64.0f,
                hypoglycemicLoad = 2.5f,
                sugarPerEquivalentG = null,
                calciumMg = null,
                ironMg = null,
                sodiumMg = null,
                cholesterolMg = null,
                seleniumMg = null,
                seleniumUg = null,
                phosphorusMg = null,
                agSaturatedG = null,
                agMonounsaturatedG = null,
                agPolyunsaturatedG = null
            ),
            FoodEntity(
                id = 0,
                category = "VERDURAS",
                food = "Brócoli cocido",
                suggestedQuantity = 0.5f,
                unit = "taza",
                netWeightG = "92",
                roundedGrossWeightG = 92,
                energyKcal = 26,
                proteinG = 2.7f,
                lipidsG = 0.4f,
                carbohydratesG = 4.6f,
                fiverG = 2.7f,
                vitaminAUgRe = 127.4f,
                ascorbicAcidMg = 68.4f,
                folicAcidUg = 46.0f,
                ironNoHemMg = 0.8f,
                potassiumMg = 268.9f,
                hypoglycemicIndex = null,
                hypoglycemicLoad = null,
                sugarPerEquivalentG = null,
                calciumMg = null,
                ironMg = null,
                sodiumMg = null,
                cholesterolMg = null,
                seleniumMg = null,
                seleniumUg = null,
                phosphorusMg = null,
                agSaturatedG = null,
                agMonounsaturatedG = null,
                agPolyunsaturatedG = null
            ),
            // Ejemplo de FRUTAS
            FoodEntity(
                id = 0,
                category = "FRUTAS",
                food = "Manzana roja con cáscara",
                suggestedQuantity = 1f,
                unit = "pieza",
                netWeightG = "138",
                roundedGrossWeightG = 150,
                energyKcal = 81,
                proteinG = 0.3f,
                lipidsG = 0.3f,
                carbohydratesG = 21.1f,
                fiverG = 3.7f,
                vitaminAUgRe = 4.1f,
                ascorbicAcidMg = 7.6f,
                folicAcidUg = 3.4f,
                ironNoHemMg = 0.2f,
                potassiumMg = 159.0f,
                hypoglycemicIndex = 38.0f,
                hypoglycemicLoad = 8.0f,
                sugarPerEquivalentG = null,
                calciumMg = null,
                ironMg = null,
                sodiumMg = null,
                cholesterolMg = null,
                seleniumMg = null,
                seleniumUg = null,
                phosphorusMg = null,
                agSaturatedG = null,
                agMonounsaturatedG = null,
                agPolyunsaturatedG = null
            )
            // NOTA: Aquí deberías agregar todos los alimentos del PDF.
            // Para una implementación completa, es mejor usar un archivo JSON
            // con todos los datos extraídos del PDF.
        )
    }
}

