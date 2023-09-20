package com.liftechnology.planalimenticio.model.usecase

import android.content.Context
import com.liftechnology.planalimenticio.data.local.repository.TableLocalRepository
import com.liftechnology.planalimenticio.model.dataclass.TypeMeals
import com.liftechnology.planalimenticio.model.dataclass.TypeTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TableUseCase(private val localRepository: TableLocalRepository) {

    suspend fun startTable(
        context: Context,
        typeTable: TypeTable,
        callback: (success: TypeTable?, error: String?) -> Unit
    ) {
        return withContext(Dispatchers.IO) {
            try {
                val mutableList: TypeTable = localRepository.startTable(context,typeTable)
                callback.invoke(mutableList, null)
            } catch (e: Exception) {
                // En caso de una excepción, se invoca el callback con el mensaje de error
                callback.invoke(null, e.message)
            }
        }
    }

    suspend fun updateTable(
        context: Context,
        typeTable: TypeTable
    ) {
        withContext(Dispatchers.IO) {
            try {
                localRepository.updateTable(context,typeTable)

            } catch (e: Exception) {

            }
        }
    }

    suspend fun getTable(
        context: Context,
        callback: (success: TypeTable?, error: String?) -> Unit
    ) {
        return withContext(Dispatchers.IO) {
            try {
                val mutableList: TypeTable = localRepository.getTable(context)
                callback.invoke(mutableList, null)
            } catch (e: Exception) {
                // En caso de una excepción, se invoca el callback con el mensaje de error
                callback.invoke(null, e.message)
            }
        }
    }

    suspend fun cleanTable(
        context: Context,
        title :Pair<String, Int>,
        callback: (success: TypeTable?, error: String?) -> Unit
    ) {
        return withContext(Dispatchers.IO) {
            try {
                val mutableList: TypeTable = localRepository.cleanTable(context, title)
                callback.invoke(mutableList, null)
            } catch (e: Exception) {
                // En caso de una excepción, se invoca el callback con el mensaje de error
                callback.invoke(null, e.message)
            }
        }
    }


}