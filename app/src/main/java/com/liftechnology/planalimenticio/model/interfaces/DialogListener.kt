package com.liftechnology.planalimenticio.model.interfaces

interface DialogListener {
    fun onDataReceivedMeals(data: Int)
    fun onDataReceivedQuantity(data: Int)
    fun onDataReceivedPortion(data: Int)
}