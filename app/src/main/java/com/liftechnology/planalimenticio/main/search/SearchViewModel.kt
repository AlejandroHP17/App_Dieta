package com.liftechnology.planalimenticio.main.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liftechnology.planalimenticio.domain.usecase.GetAllFoodsUseCase
import com.liftechnology.planalimenticio.domain.usecase.GetFoodsByCategoryUseCase
import com.liftechnology.planalimenticio.domain.usecase.SearchFoodsUseCase
import com.liftechnology.planalimenticio.mapper.subMenu.toSubMenuMapper
import com.liftechnology.planalimenticio.model.ui.subMenu.SubMenuState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel (
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val getFoodsByCategoryUseCase: GetFoodsByCategoryUseCase,
    private val searchFoodsUseCase: SearchFoodsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(SubMenuState())
    /** El estado de la UI para la pantalla. */
    val uiState: StateFlow<SubMenuState> = _uiState.asStateFlow()

    private val TAG = "SearchViewModel"

    /**
     * Carga los alimentos iniciales según la categoría.
     * Si categoria es null, muestra todos los alimentos.
     * Si categoria tiene valor, muestra solo los alimentos de esa categoría.
     * Espera a que la base de datos esté inicializada antes de cargar los alimentos.
     * 
     * @param categoria Nombre de la categoría o null para buscar en toda la base de datos
     */
    fun searchFood(categoria: String?) {
        viewModelScope.launch {
            var result = if (categoria == null) {
                // Si no hay categoría, obtener todos los alimentos
                getAllFoodsUseCase.invokeSuspend()
            } else {
                // Si hay categoría, obtener alimentos de esa categoría
                getFoodsByCategoryUseCase.invokeSuspend(categoria)
            }

            var attempts = 0
            val maxAttempts = 20 // Máximo 2 segundos (20 * 100ms)
            val delayMs = 100L // Espera 100ms entre intentos

            // Espera a que la base de datos esté inicializada
            while (result.isEmpty() && attempts < maxAttempts) {
                delay(delayMs)
                result = if (categoria == null) {
                    getAllFoodsUseCase.invokeSuspend()
                } else {
                    getFoodsByCategoryUseCase.invokeSuspend(categoria)
                }
                attempts++
                if (result.isEmpty()) {
                    Log.d(TAG, "Esperando inicialización de BD... Intento $attempts/$maxAttempts")
                }
            }

            if (result.isNotEmpty()) {
                Log.d(TAG, "✅ Alimentos cargados: ${result.size} alimentos encontrados")
                _uiState.update { 
                    it.copy(foodList = result.toSubMenuMapper(categoria == null))
                }
            } else {
                Log.w(TAG, "⚠️ No se encontraron alimentos después de $maxAttempts intentos")
                _uiState.update { 
                    it.copy(foodList = emptyList())
                }
            }
        }
    }

    /**
     * Busca alimentos por texto de búsqueda.
     * Si searchQuery está vacío, muestra todos los alimentos (de la categoría si se especifica).
     * Si categoria es null, busca en toda la base de datos.
     * Si categoria tiene valor, busca solo dentro de esa categoría.
     * 
     * @param searchQuery Texto de búsqueda
     * @param categoria Nombre de la categoría o null para buscar en toda la base de datos
     */
    fun searchByText(searchQuery: String, categoria: String? = null) {
        viewModelScope.launch {
            val result = if (searchQuery.isBlank()) {
                // Si el texto está vacío, mostrar todos los alimentos (de la categoría si se especifica)
                if (categoria == null) {
                    getAllFoodsUseCase.invokeSuspend()
                } else {
                    getFoodsByCategoryUseCase.invokeSuspend(categoria)
                }
            } else if (categoria == null) {
                // Buscar en toda la base de datos
                searchFoodsUseCase.invokeSuspend(searchQuery)
            } else {
                // Buscar dentro de una categoría específica
                // Primero obtenemos todos los alimentos de la categoría
                val categoryFoods = getFoodsByCategoryUseCase.invokeSuspend(categoria)
                // Luego filtramos por el texto de búsqueda (case-insensitive)
                categoryFoods.filter { 
                    it.food.contains(searchQuery, ignoreCase = true) 
                }
            }

            _uiState.update { 
                it.copy(foodList = result.toSubMenuMapper(categoria == null))
            }
        }
    }
}