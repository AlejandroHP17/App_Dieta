package com.liftechnology.planalimenticio.main.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import com.liftechnology.planalimenticio.main.theme.AppTheme
import com.liftechnology.planalimenticio.main.theme.colorWhite
import org.koin.androidx.compose.koinViewModel

/**
 * Actividad principal de la aplicación.
 * Esta actividad aloja el grafo de navegación principal y sirve como punto de entrada a la interfaz de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MainActivity : AppCompatActivity() {

    /**
     * Se llama cuando la actividad se crea por primera vez. Se encarga de configurar el contenido de la UI.
     *
     * @param savedInstanceState Si la actividad se está reinicializando después de haber sido cerrada,
     * este Bundle contiene los datos más recientes que suministró en [onSaveInstanceState].
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val sharedViewModel: SharedViewModel = koinViewModel()
            AppTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                        .background(color = colorWhite)
                ) {
                    AppNavHost(
                        sharedViewModel = sharedViewModel
                    )
                }
            }
        }
    }
}