package com.liftechnology.planalimenticio.domain.di

import com.liftechnology.planalimenticio.data.local.db.FoodRoomDatBase
import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import com.liftechnology.planalimenticio.data.network.enviroment.Environment
import com.liftechnology.planalimenticio.data.network.repository.FoodRepository
import com.liftechnology.planalimenticio.data.network.repository.FoodRepositoryImpl
import com.liftechnology.planalimenticio.data.network.service.ListFoodApi
import com.liftechnology.planalimenticio.domain.usecase.FoodUseCase
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

    /* Local */
    single{
        FoodLocalRepository(get())
    }

    single {
        FoodRoomDatBase.getDataBase(context = get()).FoodDao()
    }

    /* Service */
    single <FoodRepository>{
        FoodRepositoryImpl(get(), get())
    }

    single {
        FoodUseCase(get(),get())
    }

}