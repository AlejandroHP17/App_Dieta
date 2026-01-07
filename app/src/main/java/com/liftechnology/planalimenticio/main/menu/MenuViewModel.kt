package com.liftechnology.planalimenticio.main.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liftechnology.planalimenticio.domain.usecase.GetAllCategoriesUseCase
import com.liftechnology.planalimenticio.mapper.menu.toMenuMapper
import com.liftechnology.planalimenticio.model.ui.menu.MenuState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel (
    private var getAllCategoriesUseCase: GetAllCategoriesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(MenuState())
    /** El estado de la UI para la pantalla. */
    val uiState: StateFlow<MenuState> = _uiState.asStateFlow()

    private val TAG = "MenuViewModel"

    /**
     * Obtiene las categorías de la base de datos.
     * Espera a que la base de datos esté inicializada antes de cargar las categorías.
     * Esto resuelve el problema de que en la primera instalación, la UI se renderiza
     * antes de que la base de datos termine de inicializarse.
     */
    fun getCategories(){
        viewModelScope.launch {
            var result = getAllCategoriesUseCase.invokeSuspend()
            var attempts = 0
            val maxAttempts = 20 // Máximo 2 segundos (20 * 100ms)
            val delayMs = 100L // Espera 100ms entre intentos

            // Espera a que la base de datos esté inicializada
            while (result.isEmpty() && attempts < maxAttempts) {
                delay(delayMs)
                result = getAllCategoriesUseCase.invokeSuspend()
                attempts++
                if (result.isEmpty()) {
                    Log.d(TAG, "Esperando inicialización de BD... Intento $attempts/$maxAttempts")
                }
            }

            if (result.isNotEmpty()) {
                Log.d(TAG, "✅ Categorías cargadas: ${result.size} categorías encontradas")
                _uiState.update { it.copy(categoryList = result.toMenuMapper()) }
            } else {
                Log.w(TAG, "⚠️ No se encontraron categorías después de $maxAttempts intentos")
                // Aún así actualiza el estado (con lista vacía) para que la UI sepa que terminó de intentar
                _uiState.update { it.copy(categoryList = emptyList()) }
            }
        }
    }
}