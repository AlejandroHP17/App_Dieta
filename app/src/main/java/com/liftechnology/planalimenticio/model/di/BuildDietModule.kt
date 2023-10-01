package com.liftechnology.planalimenticio.model.di

import com.liftechnology.planalimenticio.ui.viewmodel.VMBuildDiet
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val buildDietModule = module{

    viewModel{
        VMBuildDiet()
    }
}