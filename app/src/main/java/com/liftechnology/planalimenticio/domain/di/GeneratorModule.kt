package com.liftechnology.planalimenticio.domain.di

import com.liftechnology.planalimenticio.data.local.repository.ListTableLocalRepository
import com.liftechnology.planalimenticio.data.local.repository.TableLocalRepository
import com.liftechnology.planalimenticio.domain.usecase.SaveUseCase

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

}