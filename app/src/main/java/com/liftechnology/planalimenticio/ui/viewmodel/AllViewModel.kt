@file:Suppress("UNCHECKED_CAST")

package com.liftechnology.planalimenticio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.data.network.repository.PrincipalRepository
import com.liftechnology.planalimenticio.model.interfaces.ActivityListener
import com.liftechnology.planalimenticio.ui.utils.ErrorCode.ERROR_APP
import com.liftechnology.planalimenticio.ui.utils.ErrorCode.ERROR_SERVICE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllViewModel () : ViewModel(){

    var listener: ActivityListener? = null


    private val _listCategories = MutableLiveData<List<CategoryResponse>>()
    val listCategories: LiveData<List<CategoryResponse>> = _listCategories



    fun buildListCategory(items: List<CategoryResponse>) {
        CoroutineScope(Dispatchers.IO).launch {
            val list: MutableList<CategoryResponse> = mutableListOf()
            items.forEach {
                list.add(it)
            }
            val listCategory: MutableList<CategoryResponse> = mutableListOf()
            listCategory.addAll(list)
            _listCategories.postValue(listCategory)
        }
    }



/*    private val _state = MutableStateFlow (UiState())
    val state: StateFlow<UiState> = _state*/



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
    data class UiState(
        val getCategories: PrincipalResponse? = null
    )
*/

}
/*
class AllViewModelFactory(private val getItemsUseCase: GetItemsUseCase): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllViewModel(getItemsUseCase) as T
    }
}

*/