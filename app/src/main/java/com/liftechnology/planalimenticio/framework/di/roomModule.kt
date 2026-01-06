package com.liftechnology.planalimenticio.framework.di

import com.liftechnology.planalimenticio.data.local.database.FoodRoomDatBase
import com.liftechnology.planalimenticio.data.local.repository.FoodLocalRepository
import com.liftechnology.planalimenticio.domain.usecase.GetAllCategoriesUseCase
import com.liftechnology.planalimenticio.domain.usecase.GetAllFoodsUseCase
import com.liftechnology.planalimenticio.domain.usecase.GetFoodByIdUseCase
import com.liftechnology.planalimenticio.domain.usecase.GetFoodCountByCategoryUseCase
import com.liftechnology.planalimenticio.domain.usecase.GetFoodsByCategoryUseCase
import com.liftechnology.planalimenticio.domain.usecase.InitializeDatabaseUseCase
import com.liftechnology.planalimenticio.domain.usecase.SearchFoodsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val roomModule = module{

    // Database de alimentos
    single { FoodRoomDatBase.getDataBase(context = get()).FoodDao() }
    singleOf(::FoodLocalRepository)

    // Use Cases de alimentos
    singleOf(::GetFoodsByCategoryUseCase)
    singleOf(::GetAllFoodsUseCase)
    singleOf(::SearchFoodsUseCase)
    singleOf(::GetFoodByIdUseCase)
    singleOf(::GetAllCategoriesUseCase)
    singleOf(::GetFoodCountByCategoryUseCase)
}