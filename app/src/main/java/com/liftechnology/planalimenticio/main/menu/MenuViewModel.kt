package com.liftechnology.planalimenticio.main.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liftechnology.planalimenticio.domain.usecase.GetAllCategoriesUseCase
import com.liftechnology.planalimenticio.mapper.menu.toMenuMapper
import com.liftechnology.planalimenticio.model.ui.menu.MenuState
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

    fun getCategories(){
        viewModelScope.launch {
            val result = getAllCategoriesUseCase.invokeSuspend()
            if (result.isNotEmpty()){
                _uiState.update { it.copy(categoryList = result.toMenuMapper()) }
            }
        }
    }
}