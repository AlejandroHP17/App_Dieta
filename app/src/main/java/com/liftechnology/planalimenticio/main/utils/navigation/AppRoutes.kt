package com.liftechnology.planalimenticio.main.utils.navigation


/**
 * Objeto centralizado que contiene todas las rutas de la aplicación organizadas por módulo.
 * Proporciona una estructura clara y mantenible para la navegación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object AppRoutes {

    /**
     * Rutas relacionadas con la autenticación (login, registro, recuperación de contraseña).
     */
    object Main {
        const val MENU = "menu"
        const val SUB_MENU = "subMenu"
    }


    /**
     * Rutas relacionadas con la pantalla de inicio (splash).
     */
    object Splash {
        const val SPLASH = "splash"
    }
}

