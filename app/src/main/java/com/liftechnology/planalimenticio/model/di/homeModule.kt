package com.liftechnology.planalimenticio.model.di

import com.liftechnology.planalimenticio.data.local.db.CategoryRoomDataBase
import com.liftechnology.planalimenticio.data.local.repository.CategoryLocalRepository
import com.liftechnology.planalimenticio.data.local.repository.ListTableLocalRepository
import com.liftechnology.planalimenticio.data.network.enviroment.Environment
import com.liftechnology.planalimenticio.data.network.repository.CategoryRepository
import com.liftechnology.planalimenticio.data.network.repository.CategoryRepositoryImpl
import com.liftechnology.planalimenticio.data.network.service.CategoryApi
import com.liftechnology.planalimenticio.model.usecase.CategoryUseCase
import com.liftechnology.planalimenticio.model.usecase.SaveUseCase
import com.liftechnology.planalimenticio.ui.viewmodel.ShareViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * single -> Crea una sola instancia, por lo que la usara una vez y se utiliza para un solo modelo
 * factory -> Crea varias instancias, por lo que se crea cada vez que se manda llamar
 * */


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

    // Registrar CategoryDao como una dependencia
    single{
        // Aquí deberías crear e inicializar tu CategoryDao
        // y retornarlo, por ejemplo:
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

    viewModel{
        ShareViewModel(get(), get())
    }
}