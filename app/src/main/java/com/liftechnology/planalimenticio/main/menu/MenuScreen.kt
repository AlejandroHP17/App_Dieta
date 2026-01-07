package com.liftechnology.planalimenticio.main.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.main.components.cards.CategoryCard
import com.liftechnology.planalimenticio.main.theme.colorWhite
import com.liftechnology.planalimenticio.main.theme.onPrimaryContainerLight
import com.liftechnology.planalimenticio.model.ui.ModelItemCard
import com.liftechnology.planalimenticio.model.ui.menu.MenuState
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true)
@Composable
private fun MenuScreenView() {
    val list = listOf(
        ModelItemCard(
            idCard = 1,
            idCategory = 1,
            title = "Prueba"
        ),
        ModelItemCard(
            idCard = 2,
            idCategory = 2,
            title = "Test"
        )
    )
    TableMenuScreen(
        uiState = MenuState(list),
        onNavigateToMain = {}

    )
}

/**
 * Pantalla de splash de la aplicación.
 *
 * @param menuViewModel El ViewModel para esta pantalla.
 * @param onNavigateToMain Lambda que se invoca para navegar a la pantalla principal.
 */
@Composable
fun MenuScreen(
    menuViewModel: MenuViewModel = koinViewModel(),
    onNavigateToMain: (String) -> Unit,
    onNavigateToSearch: () -> Unit
) {
    val uiState by menuViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        menuViewModel.getCategories()
    }

    Column() {
        HeaderScreen(
            onNavigateToSearch = {onNavigateToSearch()}
        )

        Spacer(modifier = Modifier.padding(8.dp))

        TableMenuScreen(
            uiState = uiState,
            onNavigateToMain = { onNavigateToMain(it) }
        )
    }
}

@Composable
private fun TableMenuScreen(
    uiState: MenuState,
    onNavigateToMain: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
            .padding(dimensionResource(R.dimen.margin_8dp)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.margin_16dp))
    ) {
        itemsIndexed(
            items = uiState.categoryList ?: emptyList(),
            key = { _, item: ModelItemCard -> item.title }
        ) { _, item: ModelItemCard ->
            CategoryCard(
                item = item,
                onClick = { onNavigateToMain(it) }
            )
        }
    }
}

@Composable
private fun HeaderScreen(
    onNavigateToSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(onPrimaryContainerLight)
    ) {
        Text(
            text = "GRUPOS ALIMENTICIOS",
            fontSize = dimensionResource(id = R.dimen.size_title_card).value.sp,
            color = colorWhite,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            tint = colorWhite,
            modifier = Modifier
                .padding(8.dp)
                .clickable{onNavigateToSearch()}
        )
    }
}