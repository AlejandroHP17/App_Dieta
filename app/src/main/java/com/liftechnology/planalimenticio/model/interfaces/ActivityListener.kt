package com.liftechnology.planalimenticio.model.interfaces

import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse

interface ActivityListener {

    /** Success api*/
    fun onSuccessPrincipal(items: PrincipalResponse?)
    /** error api*/
    fun onError(errorCode:Int)

}