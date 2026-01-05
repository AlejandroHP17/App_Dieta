package com.liftechnology.planalimenticio.main.subMenu

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
 * Pantalla de submenú que muestra los alimentos de una categoría específica.
 *
 * @param categoria Nombre de la categoría de alimentos a mostrar
 * @param subMenuViewModel El ViewModel para esta pantalla
 */
@Composable
fun SubMenuScreen(
    categoria: String,
    subMenuViewModel: SubMenuViewModel = koinViewModel(),
) {

    val uiState by subMenuViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(categoria) {
        // Aquí puedes usar la categoría para cargar los alimentos específicos
        subMenuViewModel.getFoodsByCategory(categoria)
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
                onClick = {  }
            )
        }
    }
}

@Preview
@Composable
private fun SubMenuScreenView()
{
    SubMenuScreen(categoria = "VERDURAS")
}