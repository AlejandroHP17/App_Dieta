package com.liftechnology.planalimenticio.domain.usecase

import android.content.Context
import com.liftechnology.planalimenticio.data.local.database.DatabaseInitializer
import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Caso de uso para inicializar la base de datos con los datos del SMAE.
 * Se ejecuta la primera vez que se abre la aplicación para poblar la base de datos
 * con los alimentos del Sistema Mexicano de Alimentos y Equivalentes.
 * 
 * @param context Contexto de la aplicación
 * @param repository Repositorio local de alimentos
 * @param dispatcher Dispatcher de corrutinas (por defecto Dispatchers.IO)
 * 
 * @author pelkidev
 */
class InitializeDatabaseUseCase(
    private val context: Context,
    private val repository: FoodLocalRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    /**
     * Ejecuta la inicialización de la base de datos si es necesaria.
     * 
     * @return Resultado de la inicialización:
     *         - InitializeResult.Success si se inicializó correctamente
     *         - InitializeResult.AlreadyInitialized si ya estaba inicializada
     *         - InitializeResult.Error si hubo un error
     */
    suspend operator fun invoke(): InitializeResult = withContext(dispatcher) {
        try {
            val initializer = DatabaseInitializer(context, repository)
            val wasInitialized = initializer.initializeIfNeeded()
            
            if (wasInitialized) {
                InitializeResult.Success
            } else {
                InitializeResult.AlreadyInitialized
            }
        } catch (e: Exception) {
            e.printStackTrace()
            InitializeResult.Error(e.message ?: "Error desconocido")
        }
    }
}

/**
 * Resultado de la inicialización de la base de datos.
 */
sealed class InitializeResult {
    /**
     * La base de datos se inicializó correctamente.
     */
    object Success : InitializeResult()
    
    /**
     * La base de datos ya estaba inicializada.
     */
    object AlreadyInitialized : InitializeResult()
    
    /**
     * Ocurrió un error durante la inicialización.
     * 
     * @param message Mensaje de error
     */
    data class Error(val message: String) : InitializeResult()
}

