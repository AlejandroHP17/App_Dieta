package com.liftechnology.planalimenticio.ui.utils

import android.app.Application
import com.liftechnology.planalimenticio.model.di.foodModule
import com.liftechnology.planalimenticio.model.di.homeModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author pelkidev
 * @date 20/08/2023
 * */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /* Inicializa la DI Koin */
        startKoin {
            androidLogger()
            modules(homeModule, foodModule)
        }
    }
}