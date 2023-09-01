package com.liftechnology.planalimenticio.model.interfaces

import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse

interface SplashListener {

    /** Success api*/
    fun onSuccessPrincipal(items: List<CategoryResponse?>)
    /** error api*/
    fun onError(errorCode:String)


}