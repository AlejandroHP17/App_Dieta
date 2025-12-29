package com.liftechnology.planalimenticio.main.subMenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.main.splash.SplashViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Pantalla de splash de la aplicación.
 *
 * @param splashViewModel El ViewModel para esta pantalla.
 * @param onNavigateToMain Lambda que se invoca para navegar a la pantalla principal.
 */
@Composable
fun SubMenuScreen(
    splashViewModel: SplashViewModel = koinViewModel(),
    onNavigateToMain: () -> Unit
) {


    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.splashscreen),
            contentDescription = "Logo",
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .align(alignment = Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun SubMenuScreenView()
{
    SubMenuScreen( onNavigateToMain = {})
}