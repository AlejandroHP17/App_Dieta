package com.liftechnology.planalimenticio.model.usecase

import android.content.Context
import com.liftechnology.planalimenticio.data.local.repository.ListTableLocalRepository
import com.liftechnology.planalimenticio.data.local.repository.TableLocalRepository
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.model.dataclass.ListTypeTable
import com.liftechnology.planalimenticio.model.dataclass.TypeTable
import com.liftechnology.planalimenticio.ui.utils.ErrorCode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveUseCase(
    private val localRepository: TableLocalRepository,
    private val listTablelocalRepository: ListTableLocalRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun saveNewTable(
        context: Context,
        name: String
    ) {
        return withContext(dispatcher) {
            val mutableList: TypeTable = localRepository.getTable(context)
            listTablelocalRepository.saveListTable(context, name, mutableList)
        }
    }

    suspend fun getListTableDiet(
        context: Context,
        callback: (success: List<ListTypeTable>?, error: String?) -> Unit
    ){
        return withContext(dispatcher) {
            try{
                val localData = listTablelocalRepository.getListTableDiet(context)
                if (localData.isNullOrEmpty()) {
                    callback.invoke(null, ErrorCode.ERROR_DB)
                }else{
                    callback.invoke(localData, null)
                }
            }catch (e: Exception) {
                // En caso de una excepción, se invoca el callback con el mensaje de error
                callback.invoke(null, ErrorCode.ERROR_READ_DB)
            }

        }
    }

    suspend fun deleteItemTableDiet(
        context: Context,
        position:Int,
        callback: (success: List<ListTypeTable>?, error: String?) -> Unit
    ){
        return withContext(dispatcher) {
            try{
                val localData = listTablelocalRepository.deleteItemTableDiet(context, position)
                if (localData.isNullOrEmpty()) {
                    callback.invoke(null, ErrorCode.ERROR_DB)
                }else{
                    callback.invoke(localData, null)
                }
            }catch (e: Exception) {
                // En caso de una excepción, se invoca el callback con el mensaje de error
                callback.invoke(null, ErrorCode.ERROR_READ_DB)
            }

        }
    }
}