package com.liftechnology.planalimenticio.model.usecase

import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.data.network.repository.MainRepository
import com.liftechnology.planalimenticio.ui.utils.ErrorCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainUseCase(
    private val repository: MainRepository
) {

    /** Obtiene el listado de categorias y procesa la informacion para el viewmodel
     * @author pelkidev
     * @date 28/08/2023
     * */
    suspend fun getCategory(
        callback: (success: List<CategoryResponse?>?, error: String?) -> Unit
    ) {
        return withContext(Dispatchers.IO) {
            try {
                val response = repository.getCategory()
                if (response != null) {
                    // Si la respuesta no es nula, se considera exitosa y se invoca el callback con los datos
                    callback.invoke(response, null)
                } else {
                    // Si la respuesta es nula, se considera un error en el servicio
                    callback.invoke(null, ErrorCode.ERROR_SERVICE)
                }
            } catch (e: Exception) {
                // En caso de una excepción, se invoca el callback con el mensaje de error
                callback.invoke(null, e.message)
            }
        }
    }
}