/**
 * @file Define el host de navegación principal de la aplicación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.liftechnology.planalimenticio.main.activity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.liftechnology.planalimenticio.main.components.ShowCustomAnimated
import com.liftechnology.planalimenticio.main.menu.MenuScreen
import com.liftechnology.planalimenticio.main.splash.SplashScreen
import com.liftechnology.planalimenticio.main.utils.navigation.AppRoutes

/**
 * Host de navegación principal de la aplicación.
 * 
 * **Responsabilidades:**
 * - Define el grafo de navegación completo de la aplicación
 * - Gestiona la navegación entre pantallas
 * - Maneja la expiración de sesión y redirige al login
 * - Muestra toasts globales
 * - Bloquea la interacción durante transiciones de navegación
 *
 * **Rutas principales:**
 * - **Splash**: Pantalla de inicio
 * - **Auth**: Login, registro, recuperación de contraseña
 * - **Main**: Menú, estudiantes, materias, calendario, perfil, etc.
 *
 * **Funcionalidades especiales:**
 * - Observa el estado de expiración de sesión y redirige automáticamente
 * - Muestra toasts globales que aparecen sobre toda la navegación
 * - Bloquea la interacción del usuario durante transiciones de pantalla
 *
 * @param sharedViewModel El ViewModel compartido para la comunicación entre pantallas y gestión de estado global.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
@Composable
fun AppNavHost(
    sharedViewModel: SharedViewModel
) {
    val navigationController = rememberNavController()
    val uiState by sharedViewModel.uiState.collectAsStateWithLifecycle()
    var isBlocked by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize()) {

        NavHost(navController = navigationController, startDestination = AppRoutes.Splash.SPLASH) {
            composable(AppRoutes.Splash.SPLASH) {
                SplashScreen(
                    onNavigateToMain = { navigationController.navigate(AppRoutes.Main.MENU) { popUpTo(AppRoutes.Splash.SPLASH) { inclusive = true } } }
                   )
            }

            // Flujo Principal
            composable(AppRoutes.Main.MENU){
                MenuScreen(
                    onNavigateToMain = { navigationController.navigate(AppRoutes.Main.MENU) { popUpTo(AppRoutes.Splash.SPLASH) { inclusive = true } } }  )
            }
            /*composable(AppRoutes.Main.SUB_MENU){ RegisterUserScreen(
                navController = navigationController,
                sharedViewModel = sharedViewModel,
                ) }
*/
        }

        // Toast global que se muestra por encima de toda la navegación
        ShowCustomAnimated(
            message = stringResource(id = uiState.controlToast.messageToast),
            isVisible = uiState.controlToast.showToast,
            typeToast = uiState.controlToast.typeToast,
            onDismiss = {
                sharedViewModel.hideToast()
            }
        )

        if (isBlocked) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitPointerEvent()
                            }
                        }
                    }
            )
        }
    }

    DisposableEffect(navigationController) {
        val listener = NavController.OnDestinationChangedListener { _, _, _ ->
            isBlocked = true
        }
        navigationController.addOnDestinationChangedListener(listener)
        onDispose { navigationController.removeOnDestinationChangedListener(listener) }
    }

    val currentEntry by navigationController.currentBackStackEntryAsState()

    DisposableEffect(currentEntry) {
        isBlocked = currentEntry?.lifecycle?.currentState != Lifecycle.State.RESUMED

        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> isBlocked = false
                Lifecycle.Event.ON_CREATE,
                Lifecycle.Event.ON_START,
                Lifecycle.Event.ON_PAUSE,
                Lifecycle.Event.ON_STOP -> isBlocked = true
                else -> { /* no-op */ }
            }
        }
        currentEntry?.lifecycle?.addObserver(observer)
        onDispose { currentEntry?.lifecycle?.removeObserver(observer) }
    }


}
