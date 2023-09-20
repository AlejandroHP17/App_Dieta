package com.liftechnology.planalimenticio.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liftechnology.planalimenticio.model.dataclass.TypeTable
import com.liftechnology.planalimenticio.model.usecase.TableUseCase
import kotlinx.coroutines.launch

class VMTable(
    private val useCase: TableUseCase
) : ViewModel() {

    /* Variables para live data */
    private val _dataFlow = MutableLiveData<TypeTable>()
    val dataFlow: LiveData<TypeTable> = _dataFlow

    private val _typeClick = MutableLiveData("")
    val typeClick: LiveData<String> = _typeClick

    var numberMeals: Int = 5


    fun startTable(context: Context, typeTable: TypeTable) {
        viewModelScope.launch {
            useCase.startTable(context, typeTable) { success, error ->
                if (error.isNullOrEmpty()) {
                    _dataFlow.postValue(success)
                }
            }
        }
    }

    fun onClickNumberMeals() {
        _typeClick.postValue("number")
    }
    fun onClickResetMeals() {
        _typeClick.postValue("clean")
    }

    fun getTable(context: Context){
        viewModelScope.launch {
            useCase.getTable(context) { success, error ->
                if (error.isNullOrEmpty()) {
                    _dataFlow.postValue(success)
                }
            }
        }
    }

    fun updateTable(context: Context, typeTable: TypeTable){
        viewModelScope.launch {
            useCase.updateTable(context, typeTable)
        }
    }

    fun cleanTable(context: Context, title :Pair<String, Int>){
        viewModelScope.launch {
            useCase.cleanTable(context, title){ success, error ->
                if (error.isNullOrEmpty()) {
                    _dataFlow.postValue(success)
                }
            }
        }
    }

}
