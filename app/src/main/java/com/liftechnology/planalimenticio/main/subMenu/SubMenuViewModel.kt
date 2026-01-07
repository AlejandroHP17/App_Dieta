package com.liftechnology.planalimenticio.main.subMenu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liftechnology.planalimenticio.domain.usecase.GetFoodsByCategoryUseCase
import com.liftechnology.planalimenticio.mapper.subMenu.toSubMenuMapper
import com.liftechnology.planalimenticio.model.ui.subMenu.SubMenuState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubMenuViewModel (
    private var getFoodsByCategoryUseCase: GetFoodsByCategoryUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(SubMenuState())
    /** El estado de la UI para la pantalla. */
    val uiState: StateFlow<SubMenuState> = _uiState.asStateFlow()

    private val TAG = "SubMenuViewModel"

    /**
     * Obtiene los alimentos de una categoría específica.
     * Espera a que la base de datos esté inicializada antes de cargar los alimentos.
     */
    fun getFoodsByCategory(category: String){
        viewModelScope.launch {
            var result = getFoodsByCategoryUseCase.invokeSuspend(category)
            var attempts = 0
            val maxAttempts = 20 // Máximo 2 segundos (20 * 100ms)
            val delayMs = 100L // Espera 100ms entre intentos

            // Espera a que la base de datos esté inicializada
            while (result.isEmpty() && attempts < maxAttempts) {
                delay(delayMs)
                result = getFoodsByCategoryUseCase.invokeSuspend(category)
                attempts++
                if (result.isEmpty()) {
                    Log.d(TAG, "Esperando inicialización de BD para categoría '$category'... Intento $attempts/$maxAttempts")
                }
            }

            if (result.isNotEmpty()) {
                Log.d(TAG, "✅ Alimentos cargados para categoría '$category': ${result.size} alimentos encontrados")
                _uiState.update { it.copy(foodList = result.toSubMenuMapper(false)) }
            } else {
                Log.w(TAG, "⚠️ No se encontraron alimentos para la categoría '$category' después de $maxAttempts intentos")
                _uiState.update { it.copy(foodList = emptyList()) }
            }
        }
    }
}