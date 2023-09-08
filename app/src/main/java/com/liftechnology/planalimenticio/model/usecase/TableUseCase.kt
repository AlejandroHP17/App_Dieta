package com.liftechnology.planalimenticio.model.usecase

import android.content.Context
import com.liftechnology.planalimenticio.data.local.repository.TableLocalRepository
import com.liftechnology.planalimenticio.model.dataclass.TypeMeals
import com.liftechnology.planalimenticio.ui.utils.ErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TableUseCase(private val localRepository: TableLocalRepository) {

    suspend fun getTable(
        context: Context,
        callback: (success: List<TypeMeals>?, error: String?) -> Unit
    ) {
        return withContext(Dispatchers.IO) {
            try {
                val mutableList: MutableList<TypeMeals>? = localRepository.getFirstTable(context)
                val list: List<TypeMeals> = mutableList.orEmpty()
                callback.invoke(list, null)
            } catch (e: Exception) {
                // En caso de una excepción, se invoca el callback con el mensaje de error
                callback.invoke(null, e.message)
            }
        }
    }
}