package com.liftechnology.planalimenticio.model.di

import com.liftechnology.planalimenticio.data.local.repository.ListTableLocalRepository
import com.liftechnology.planalimenticio.data.local.repository.TableLocalRepository
import com.liftechnology.planalimenticio.model.usecase.FoodUseCase
import com.liftechnology.planalimenticio.model.usecase.SaveUseCase
import com.liftechnology.planalimenticio.ui.viewmodel.VMGenerator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val generatorModule = module{


    single{
        TableLocalRepository()
    }
    single{
        ListTableLocalRepository()
    }
    single {
        SaveUseCase(get(),get())
    }

    viewModel{
        VMGenerator(get())
    }
}