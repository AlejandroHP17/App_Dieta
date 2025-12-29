package com.liftechnology.planalimenticio.framework

import android.app.Application
import com.liftechnology.planalimenticio.domain.di.*
import org.koin.android.ext.koin.androidContext
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
            androidContext(this@BaseApplication)
            modules(
                homeModule,
                //foodModule,
                //tableModule,
                //generatorModule,
                //buildDietModule
            )
        }
    }
}