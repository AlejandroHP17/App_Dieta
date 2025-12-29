package com.liftechnology.planalimenticio.domain.usecase

import android.content.Context
import com.liftechnology.planalimenticio.data.local.repository.TableLocalRepository
import com.liftechnology.planalimenticio.domain.dataclass.TypeTable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TableUseCase(
    private val localRepository: TableLocalRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
    ) {

    suspend fun startTable(
        context: Context,
        typeTable: TypeTable,
        callback: (success: TypeTable?, error: String?) -> Unit
    ) {
        return withContext(dispatcher) {
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
        return withContext(dispatcher) {
            localRepository.updateTable(context,typeTable)
        }
    }

    suspend fun getTable(
        context: Context,
        callback: (success: TypeTable?, error: String?) -> Unit
    ) {
        return withContext(dispatcher) {
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
        return withContext(dispatcher) {
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