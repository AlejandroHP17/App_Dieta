package com.liftechnology.planalimenticio.model.usecase

import android.content.Context
import com.liftechnology.planalimenticio.data.local.repository.ListTableLocalRepository
import com.liftechnology.planalimenticio.data.local.repository.TableLocalRepository
import com.liftechnology.planalimenticio.model.dataclass.TypeTable
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
}