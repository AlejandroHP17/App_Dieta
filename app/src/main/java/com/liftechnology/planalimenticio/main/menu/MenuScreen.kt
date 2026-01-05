package com.liftechnology.planalimenticio.main.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.main.components.cards.CategoryCard
import com.liftechnology.planalimenticio.model.ui.ModelItemCard
import org.koin.androidx.compose.koinViewModel

/**
 * Pantalla de splash de la aplicación.
 *
 * @param menuViewModel El ViewModel para esta pantalla.
 * @param onNavigateToMain Lambda que se invoca para navegar a la pantalla principal.
 */
@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel = koinViewModel(),
    onNavigateToMain: (String) -> Unit
) {
    val uiState by menuViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        menuViewModel.getCategories()
    }

    LazyColumn(
        modifier = Modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_16dp))
    ) {
        itemsIndexed(
            items = uiState.categoryList ?: emptyList(),
            key = { _ , item: ModelItemCard -> item.title }
        ) { _, item: ModelItemCard ->
            CategoryCard(
                item = item,
                onClick = { onNavigateToMain(it) }
            )
        }
    }
}

@Preview
@Composable
private fun MenuScreenView()
{
    MenuScreen( onNavigateToMain = {})
}