package com.liftechnology.planalimenticio.framework

import android.app.Application
import android.util.Log
import com.liftechnology.planalimenticio.domain.usecase.InitializeDatabaseUseCase
import com.liftechnology.planalimenticio.domain.usecase.InitializeResult
import com.liftechnology.planalimenticio.framework.di.homeModule
import com.liftechnology.planalimenticio.framework.di.initialModule
import com.liftechnology.planalimenticio.framework.di.roomModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author pelkidev
 * @date 20/08/2023
 * */
class BaseApplication : Application() {
    
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    
    override fun onCreate() {
        super.onCreate()

        /* Inicializa la DI Koin */
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                initialModule,
                roomModule,
                homeModule
            )
        }
        
        // Inicializar la base de datos con los datos del SMAE
        initializeDatabase()
    }
    
    /**
     * Inicializa la base de datos con los datos del Sistema Mexicano de Alimentos y Equivalentes.
     * Se ejecuta en background para no bloquear el hilo principal.
     */
    private fun initializeDatabase() {
        applicationScope.launch {
            try {
                val initializeUseCase: InitializeDatabaseUseCase = get()
                when (val result = initializeUseCase()) {
                    is InitializeResult.Success -> {
                        Log.d("BaseApplication", "✅ Base de datos inicializada correctamente con datos del SMAE")
                    }
                    is InitializeResult.AlreadyInitialized -> {
                        Log.d("BaseApplication", "ℹ️ Base de datos ya estaba inicializada")
                    }
                    is InitializeResult.Error -> {
                        Log.e("BaseApplication", "❌ Error al inicializar la base de datos: ${result.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e("BaseApplication", "❌ Excepción al inicializar la base de datos", e)
            }
        }
    }
}