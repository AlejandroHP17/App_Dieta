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
        const val SUB_MENU = "subMenu/{category}"
        const val SEARCH = "search/{category}"  // El parámetro será opcional mediante defaultValue
        
        /**
         * Construye la ruta de búsqueda con la categoría especificada.
         * Si category es null, usa "all" como valor por defecto para buscar en toda la base de datos.
         * 
         * @param category Categoría para filtrar la búsqueda, o null para buscar en todas las categorías
         * @return Ruta formateada para la pantalla de búsqueda
         */
        fun buildSearchRoute(category: String? = null): String {
            val categoryValue = category ?: "all"
            return "search/$categoryValue"
        }
    }


    /**
     * Rutas relacionadas con la pantalla de inicio (splash).
     */
    object Splash {
        const val SPLASH = "splash"
    }
}

