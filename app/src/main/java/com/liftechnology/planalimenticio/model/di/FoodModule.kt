package com.liftechnology.planalimenticio.model.di

import com.liftechnology.planalimenticio.data.network.enviroment.Environment
import com.liftechnology.planalimenticio.data.network.repository.ListFoodRepository
import com.liftechnology.planalimenticio.data.network.repository.ListFoodRepositoryImpl
import com.liftechnology.planalimenticio.data.network.service.ListFoodApi
import com.liftechnology.planalimenticio.model.usecase.ListFoodUseCase
import com.liftechnology.planalimenticio.ui.viewmodel.VMCategory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val foodModule = module {
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Environment.URL_BASE)
            .build()
            .create(ListFoodApi::class.java)
    }

    single <ListFoodRepository>{
        ListFoodRepositoryImpl(get())
    }

    single {
        ListFoodUseCase(get())
    }

    viewModel {
        VMCategory(get())
    }
}