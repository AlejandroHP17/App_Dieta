package com.liftechnology.planalimenticio.clase

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainRepositoryImpl(
    private val api: MyApi
) : MainRepository {

    override fun doNetworkCall() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.callApi()
            Log.d("PelkiRed",response.body()?.result.toString())
        }

    }
}