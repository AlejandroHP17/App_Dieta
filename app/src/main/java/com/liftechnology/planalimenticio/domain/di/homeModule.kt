package com.liftechnology.planalimenticio.domain.di

import com.liftechnology.planalimenticio.data.local.db.CategoryRoomDataBase
import com.liftechnology.planalimenticio.data.local.repository.CategoryLocalRepository
import com.liftechnology.planalimenticio.data.local.repository.ListTableLocalRepository
import com.liftechnology.planalimenticio.data.network.enviroment.Environment
import com.liftechnology.planalimenticio.data.network.repository.CategoryRepository
import com.liftechnology.planalimenticio.data.network.repository.CategoryRepositoryImpl
import com.liftechnology.planalimenticio.data.network.service.CategoryApi
import com.liftechnology.planalimenticio.domain.usecase.CategoryUseCase
import com.liftechnology.planalimenticio.domain.usecase.SaveUseCase
import com.liftechnology.planalimenticio.main.activity.SharedViewModel
import com.liftechnology.planalimenticio.main.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.core.module.dsl.viewModelOf


val homeModule = module{
    single{
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Environment.URL_BASE)
            .build()
            .create(CategoryApi::class.java)
    }

    single{
        CategoryLocalRepository(get())
    }

    single{
        CategoryRoomDataBase.getDataBase(context = get()).CategoryDao()
    }

    single<CategoryRepository> {
        CategoryRepositoryImpl(get(),get()) }

    single{
        ListTableLocalRepository()
    }

    single{
        CategoryUseCase(get(),get())
    }

    single { SaveUseCase(get(),get()) }

    viewModelOf(::SharedViewModel)
    viewModelOf(::SplashViewModel)
}