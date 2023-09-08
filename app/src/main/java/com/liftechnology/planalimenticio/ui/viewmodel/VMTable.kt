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
    private val _dataFlow = MutableStateFlow<List<TypeMeals>>(emptyList())
    val dataFlow: StateFlow<List<TypeMeals>> = _dataFlow

    private val _typeClick = MutableLiveData<String>()
    val typeClick: LiveData<String> = _typeClick

    var numberMeals: Int = 5


    fun startTable(context: Context) {
        viewModelScope.launch {
            useCase.getTable(context) { success, error ->
                if (error.isNullOrEmpty()) {
                    _dataFlow.value = success!!
                }
            }
        }
    }

    fun onClickNumberMeals() {
        _typeClick.value = "something"
        //Logica para enviar dos datos
    }

}
