package com.liftechnology.planalimenticio.model.di

import com.liftechnology.planalimenticio.data.local.repository.TableLocalRepository
import com.liftechnology.planalimenticio.model.usecase.TableUseCase
import com.liftechnology.planalimenticio.ui.viewmodel.VMTable
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

    viewModel {
        VMTable(get())
    }
}