package com.liftechnology.planalimenticio.framework.di

import com.liftechnology.planalimenticio.main.activity.SharedViewModel
import com.liftechnology.planalimenticio.main.menu.MenuViewModel
import com.liftechnology.planalimenticio.main.search.SearchViewModel
import com.liftechnology.planalimenticio.main.splash.SplashViewModel
import com.liftechnology.planalimenticio.main.subMenu.SubMenuViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val homeModule = module{
    viewModelOf(::SharedViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::MenuViewModel)
    viewModelOf(::SubMenuViewModel)
    viewModelOf(::SearchViewModel)
}