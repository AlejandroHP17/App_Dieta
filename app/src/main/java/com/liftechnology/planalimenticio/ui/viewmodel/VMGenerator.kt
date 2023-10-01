package com.liftechnology.planalimenticio.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.liftechnology.planalimenticio.data.network.models.response.local.ModelCardList
import com.liftechnology.planalimenticio.framework.CoroutineScopeManager
import com.liftechnology.planalimenticio.model.dataclass.ListTypeTable
import com.liftechnology.planalimenticio.model.usecase.SaveUseCase
import com.liftechnology.planalimenticio.ui.utils.ErrorCode
import kotlinx.coroutines.launch

class VMGenerator(
    private val useCaseGenerator: SaveUseCase
) : ViewModel() {

    private val _listGenerator = MutableLiveData<List<ListTypeTable>>()
    val listGenerator: LiveData<List<ListTypeTable>> = _listGenerator

    private val _listGeneratorEmpty = MutableLiveData<String>()
    val listGeneratorEmpty: LiveData<String> = _listGeneratorEmpty

    // Corrutina controlada
    private val coroutine = CoroutineScopeManager()

    fun getListTable(context: Context) {
        coroutine.scopeIO.launch {

            useCaseGenerator.getListTable(context){success, error ->
                if (error.isNullOrEmpty()){
                    _listGenerator.postValue(success!!)
                }else{
                    _listGeneratorEmpty.postValue(error)
                }
            }
        }
    }

    fun deleteItemTable(context: Context, position: Int) {
        coroutine.scopeIO.launch {
            useCaseGenerator.deleteItemTableDiet(context,position) { success, error ->
                if (error.isNullOrEmpty()) {
                    _listGenerator.postValue(success!!)
                } else {
                    _listGeneratorEmpty.postValue(error)
                }
            }
        }
    }

}