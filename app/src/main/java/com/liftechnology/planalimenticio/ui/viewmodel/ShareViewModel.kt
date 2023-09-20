package com.liftechnology.planalimenticio.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.model.interfaces.SplashListener
import com.liftechnology.planalimenticio.model.usecase.CategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShareViewModel(
    private val useCase: CategoryUseCase

    ) : ViewModel() {

    // Variable que inicializa el listener con el SplashActivity
    var listener: SplashListener? = null

    /* Variables para live data */
    private val _dataFlow = MutableStateFlow<List<CategoryResponse>>(emptyList())
    val dataFlow: StateFlow<List<CategoryResponse>> = _dataFlow

    var colorGeneral: String? = null

    /** Apenas inicia el viewmodel sale por el servicio para obtener las categorias
     * @author pelkidev
     * @date 28/08/2023
     * */
    init {
        viewModelScope.launch {
            /** Manda a llamar el usecase
             * success -> Obtiene la respuesta del listado correctamente
             * error -> Nos devuelve el tipo de error producido
             * */
            useCase.getCategory() { success, error ->
                if (error.isNullOrEmpty()) {
                    listener?.onSuccessPrincipal(success!!)
                } else {
                    listener?.onError(error)
                }
            }
        }
    }


    /** Método para mandar a llamar el listado de alimentos por categoria
     * @author pelkidev
     * @date 20/08/2023
     * @param [items] lista de categorias
     * */
    fun buildListCategory(items: List<CategoryResponse>) {
        viewModelScope.launch {
            val list: MutableList<CategoryResponse> = mutableListOf()
            items.forEach {
                list.add(it)
            }
            val listCategory: MutableList<CategoryResponse> = mutableListOf()
            listCategory.addAll(list)
            _dataFlow.value = listCategory
        }
    }


}
