package com.liftechnology.planalimenticio.data.network.retrofit

import com.google.gson.GsonBuilder
import com.liftechnology.planalimenticio.data.network.enviroment.Environment
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Environment.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }
}