package com.liftechnology.planalimenticio.domain.di

import com.liftechnology.planalimenticio.data.local.repository.TableLocalRepository
import com.liftechnology.planalimenticio.domain.usecase.TableUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tableModule = module {

    /* Local */
    single{
        TableLocalRepository()
    }

    single {
        TableUseCase(get())
    }

}