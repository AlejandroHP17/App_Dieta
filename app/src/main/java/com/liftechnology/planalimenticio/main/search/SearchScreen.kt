package com.liftechnology.planalimenticio.main.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.main.components.cards.FoodCard
import com.liftechnology.planalimenticio.main.components.input.TextFieldGeneric
import com.liftechnology.planalimenticio.main.theme.colorWhite
import com.liftechnology.planalimenticio.main.theme.onPrimaryContainerLight
import com.liftechnology.planalimenticio.main.utils.regex.ModelRegex
import com.liftechnology.planalimenticio.model.ModelSubItemCard
import com.liftechnology.planalimenticio.model.ui.subMenu.SubMenuState
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true)
@Composable
private fun SearchScreenView() {

}

@Composable
fun SearchScreen(
    categoria: String? = null,  // Ahora es nullable para permitir búsquedas sin categoría
    searchViewModel: SearchViewModel = koinViewModel(),
) {

    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(categoria) {
       searchViewModel.searchFood(categoria)
    }

    // Solicitar foco y mostrar teclado cuando la pantalla se compone por primera vez
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column() {
        HeaderSearchScreen(title = categoria)

        Spacer(modifier = Modifier.padding(8.dp))

        SearchSectionScreen(
            onWordSearch = { searchQuery ->
                // Cuando el usuario escribe, buscar por texto
                searchViewModel.searchByText(searchQuery, categoria)
            },
            focusRequester = focusRequester
        )

        Spacer(modifier = Modifier.padding(8.dp))

        TableSearchMenuScreen(
            uiState = uiState
        )
    }
}

@Composable
private fun TableSearchMenuScreen(
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


@Composable
private fun HeaderSearchScreen(
    title: String?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(onPrimaryContainerLight)
    ) {
        Text(
            text = title ?: "Todas las Categorias",
            fontSize = dimensionResource(id = R.dimen.size_title_card).value.sp,
            color = colorWhite,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
        )
    }
}

@Composable
private fun SearchSectionScreen(
    onWordSearch: (String) -> Unit,
    focusRequester: FocusRequester
){
    TextFieldGeneric(
        regex = ModelRegex.SIMPLE_TEXT,
        onBoxChanged = {onWordSearch(it)},
        focusRequester = focusRequester
    )
}