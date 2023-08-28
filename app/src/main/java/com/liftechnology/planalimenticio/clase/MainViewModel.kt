package com.liftechnology.planalimenticio.clase

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel (
    private val repository:MainRepository
    ) : ViewModel() {

    fun doNetworkCall(){
        val a = repository.doNetworkCall()
        Log.d("PelkiVM", a.toString())
    }
}