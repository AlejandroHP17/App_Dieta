package com.liftechnology.planalimenticio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.liftechnology.planalimenticio.data.network.models.response.VegetableResponse
import com.liftechnology.planalimenticio.data.network.service.SecondaryRetofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VMCategory : ViewModel() {
    private val _getVegetable = MutableLiveData<List<VegetableResponse>>()
    val getVegetable: LiveData<List<VegetableResponse>> = _getVegetable

    fun getItemVegetable(url:String){
        CoroutineScope(Dispatchers.IO).launch {
            try{
                SecondaryRetofitService().getItemsVegetable(url){success, error ->
                if (error.isNullOrEmpty()){
                    _getVegetable.postValue(success?.result)
                }

                }
            }catch (e:java.lang.Exception){}
        }
    }

}