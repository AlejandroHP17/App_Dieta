package com.liftechnology.planalimenticio.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
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
import kotlinx.coroutines.launch

//class AllViewModel : ViewModel(){
class AllViewModel : ViewModel(){

    var listener: ActivityListener? = null

    /** Variable of Observes*/
    private val _launchError = MutableLiveData <String>()
    val launchError: LiveData<String> = _launchError

    private val _getCategories = MutableLiveData <PrincipalResponse>()
    val getCategories: LiveData<PrincipalResponse> = _getCategories

    val argumentValue = MutableLiveData<List<CategoryResponse>>()

    fun getFirstService(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = PrincipalRepository().getCategories()
            // Ask if the response is successful
            if (response.isSuccessful){
                /** Observes */
                if (response.body() != null) {
                    listener?.onSuccessPrincipal(response.body())
                } else {
                    listener?.onError(0)
                    _launchError.postValue(ERROR_APP)
                }
            }else{
                /** Observes */
                listener?.onError(1)
                _launchError.postValue(ERROR_SERVICE)
            }
        }
    }
}