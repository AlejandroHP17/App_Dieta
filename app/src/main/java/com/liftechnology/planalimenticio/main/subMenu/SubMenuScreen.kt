package com.liftechnology.planalimenticio.main.subMenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.main.components.cards.FoodCard
import com.liftechnology.planalimenticio.main.components.common.HeaderScreen
import com.liftechnology.planalimenticio.model.ModelSubItemCard
import com.liftechnology.planalimenticio.model.ui.subMenu.SubMenuState
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
private fun SubMenuScreenView()
{
    SubMenuScreen(
        categoria = "VERDURAS",
        onNavigateToSearch = {})
}


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
    onNavigateToSearch: (String) -> Unit
) {

    val uiState by subMenuViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(categoria) {
        // Aquí puedes usar la categoría para cargar los alimentos específicos
        subMenuViewModel.getFoodsByCategory(categoria)
    }

    Column {
        HeaderScreen(
            title = categoria,
            onNavigateToSearch = { onNavigateToSearch(categoria) }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        TableSubMenuScreen(
            uiState = uiState
        )
    }
}

@Composable
private fun TableSubMenuScreen(
    uiState: SubMenuState
){
    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
            .padding(dimensionResource(R.dimen.margin_8dp)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_16dp))
    ) {
        itemsIndexed(
            items = uiState.foodList ?: emptyList(),
            key = { _ , item: ModelSubItemCard -> item.idCard }
        ) { _, item: ModelSubItemCard ->
            FoodCard(
                item = item
            )
        }
    }
}