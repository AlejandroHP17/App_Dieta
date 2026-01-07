package com.liftechnology.planalimenticio.framework.di

import com.liftechnology.planalimenticio.domain.usecase.InitializeDatabaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val initialModule = module {
    // Dispatcher para operaciones de base de datos
    single<CoroutineDispatcher> { Dispatchers.IO }

    // Use Cases de alimentos
    singleOf(::InitializeDatabaseUseCase)
}