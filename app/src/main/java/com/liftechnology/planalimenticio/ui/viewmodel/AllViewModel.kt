@file:Suppress("UNCHECKED_CAST")

package com.liftechnology.planalimenticio.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import com.liftechnology.planalimenticio.data.network.repository.PrincipalRepository
import com.liftechnology.planalimenticio.model.interfaces.ActivityListener
import com.liftechnology.planalimenticio.ui.utils.ErrorCode.ERROR_APP
import com.liftechnology.planalimenticio.ui.utils.ErrorCode.ERROR_SERVICE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AllViewModel (): ViewModel(){

    var listener: ActivityListener? = null


    private val _state = MutableStateFlow (UiState())
    val state: StateFlow<UiState> = _state

    val argumentValue = MutableLiveData<List<CategoryResponse>>()

    init{
        CoroutineScope(Dispatchers.IO).launch {
            val response = PrincipalRepository().getCategories()
            // Ask if the response is successful
            if (response.isSuccessful){
                /** Observes */
                if (response.body() != null) {
                    listener?.onSuccessPrincipal(response.body())
                } else {
                    listener?.onError(ERROR_APP)
                }
            }else{
                /** Observes */
                listener?.onError(ERROR_SERVICE)
            }
        }
    }
/*
    fun getFirstService() {
        CoroutineScope(Dispatchers.IO).launch {
            val items = getItemsUseCase.execute()
            listener?.onSuccessPrincipal(items.body())
        }
    }
*/

    data class UiState(
        val getCategories: PrincipalResponse? = null
    )

}
/*
class AllViewModelFactory(private val getItemsUseCase: GetItemsUseCase): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllViewModel(getItemsUseCase) as T
    }
}

*/