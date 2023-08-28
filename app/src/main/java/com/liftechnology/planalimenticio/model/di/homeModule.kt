package com.liftechnology.planalimenticio.model.di

import com.google.gson.GsonBuilder
import com.liftechnology.planalimenticio.data.network.enviroment.Environment
import com.liftechnology.planalimenticio.data.network.service.PrincipalApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val homeModule = module{

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Environment.URL_BASE)
            .build()
            .create(PrincipalApi::class.java)
    }
}