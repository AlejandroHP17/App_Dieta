package com.liftechnology.planalimenticio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.liftechnology.planalimenticio.data.network.models.response.FoodResponse
import com.liftechnology.planalimenticio.data.network.service.SecondaryRetofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VMCategory : ViewModel() {
    private val _getVegetable = MutableLiveData<List<FoodResponse>>()
    val getVegetable: LiveData<List<FoodResponse>> = _getVegetable

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