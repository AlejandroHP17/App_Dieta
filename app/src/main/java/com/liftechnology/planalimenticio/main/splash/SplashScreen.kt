package com.liftechnology.planalimenticio.main.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.liftechnology.planalimenticio.R

/**
 * Pantalla de splash de la aplicación.
 *
 * @param onNavigateToMain Lambda que se invoca para navegar a la pantalla principal.
 */
@Composable
fun SplashScreen(
    onNavigateToMain: () -> Unit
) {

    LaunchedEffect(Unit) {
        onNavigateToMain()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.splashscreen),
            contentDescription = "Logo",
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        )
    }
}

@Preview
@Composable
private fun SplashScreenView()
{
    SplashScreen( onNavigateToMain = {})
}