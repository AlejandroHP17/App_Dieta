package com.liftechnology.planalimenticio.model.event

import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.model.ui.ToastUiState

/**
 * Representa el estado de la UI compartido entre diferentes pantallas.
 *
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ShareUiState(
    val controlToast: ToastUiState = ToastUiState(R.string.app_name, false),
)
