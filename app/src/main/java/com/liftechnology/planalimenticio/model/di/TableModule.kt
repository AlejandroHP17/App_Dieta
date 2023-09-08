package com.liftechnology.planalimenticio.model.di

import com.liftechnology.planalimenticio.data.local.db.FoodRoomDatBase
import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import com.liftechnology.planalimenticio.data.local.repository.TableLocalRepository
import com.liftechnology.planalimenticio.data.network.repository.FoodRepository
import com.liftechnology.planalimenticio.data.network.repository.FoodRepositoryImpl
import com.liftechnology.planalimenticio.model.usecase.FoodUseCase
import com.liftechnology.planalimenticio.model.usecase.TableUseCase
import com.liftechnology.planalimenticio.ui.viewmodel.VMCategory
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