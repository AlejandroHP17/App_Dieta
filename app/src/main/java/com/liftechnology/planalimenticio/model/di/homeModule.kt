package com.liftechnology.planalimenticio.model.di

import com.google.gson.GsonBuilder
import com.liftechnology.planalimenticio.clase.MainRepository
import com.liftechnology.planalimenticio.clase.MainRepositoryImpl
import com.liftechnology.planalimenticio.clase.MainViewModel
import com.liftechnology.planalimenticio.clase.MyApi
import com.liftechnology.planalimenticio.data.network.enviroment.Environment
import com.liftechnology.planalimenticio.data.network.service.PrincipalApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val homeModule = module{
    single{
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Environment.URL_BASE)
            .build()
            .create(MyApi::class.java)
    }

    /**
     * single -> Crea una sola instancia, por lo que la usara una vez y se utiliza para un solo modelo
     * factory -> Crea varias instancias, por lo que se crea cada vez que se manda llamar */
    single<MainRepository> {
        MainRepositoryImpl(get()) }

    viewModel{
        MainViewModel(get())
    }
}