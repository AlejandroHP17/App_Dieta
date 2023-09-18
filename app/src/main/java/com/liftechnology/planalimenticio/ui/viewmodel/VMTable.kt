package com.liftechnology.planalimenticio.ui.viewmodel

import android.content.Context
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.data.network.models.response.local.ModelCardList
import com.liftechnology.planalimenticio.model.dataclass.TypeMeals
import com.liftechnology.planalimenticio.model.dataclass.TypeTable
import com.liftechnology.planalimenticio.model.usecase.TableUseCase
import com.liftechnology.planalimenticio.ui.utils.CustomDetailDialog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

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
            useCase.readTable(context, typeTable) { success, error ->
                if (error.isNullOrEmpty()) {
                    _dataFlow.postValue(success)
                }
            }
        }
    }

    fun onClickNumberMeals() {
        _typeClick.postValue("something")
    }

    fun updateTable(context: Context, typeTable: TypeTable){
        viewModelScope.launch {
            useCase.updateTable(context, typeTable)
        }
    }

}
