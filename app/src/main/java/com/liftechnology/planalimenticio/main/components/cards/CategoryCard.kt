package com.liftechnology.planalimenticio.main.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.main.theme.colorBgCard
import com.liftechnology.planalimenticio.model.ui.ModelItemCard

/**
 * Obtiene el color de la tarjeta según el ID de categoría.
 * Cada categoría tiene un color único para facilitar la identificación visual.
 * 
 * @param idCategory ID de la categoría (1-17)
 * @return Color correspondiente a la categoría
 */
@Composable
private fun getCategoryColor(idCategory: Int): Color {
    return when (idCategory) {
        1 -> Color(0xFFFFE5E5) // Aceite y grasas con proteína - Rosa claro
        2 -> Color(0xFFFFF4E5) // Aceites y grasas - Naranja claro
        3 -> Color(0xFFFFE5F0) // Alimentos de origen animal alto aporte de grasa - Rosa pastel
        4 -> Color(0xFFE5F5FF) // Alimentos de origen animal bajo aporte de grasa - Azul claro
        5 -> Color(0xFFE8F5E9) // Alimentos de origen animal moderado aporte de grasa - Verde claro
        6 -> Color(0xFFF0E5FF) // Alimentos de origen animal muy bajo aporte de grasa - Morado claro
        7 -> Color(0xFFFFF0E5) // Azúcares con grasa - Melocotón claro
        8 -> Color(0xFFFFFCE5) // Azúcares sin grasa - Amarillo claro
        9 -> Color(0xFFE0F2E0) // Cereales con grasa - Verde menta
        10 -> Color(0xFFC8E6C9) // Cereales sin grasa - Verde pastel
        11 -> Color(0xFFFFE5F5) // Frutas - Rosa suave
        12 -> Color(0xFFE3F2FD) // Leche con azúcar - Azul cielo
        13 -> Color(0xFFE1F5FE) // Leche descremada - Azul muy claro
        14 -> Color(0xFFFFF9E6) // Leche entera - Crema
        15 -> Color(0xFFF3E5F5) // Leche semidescremada - Lavanda claro
        16 -> Color(0xFFFFEBEE) // Leguminosas - Salmón claro
        17 -> Color(0xFFE8F5E9) // Verduras - Verde claro
        else -> colorBgCard // Color por defecto si no coincide
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryCardView() {
    val data = ModelItemCard(
        idCard = 1,
        idCategory = 17,
        title = "Verduras"
    )
    CategoryCard(
        item = data,
        onClick = {}
    )
}

@Composable
fun CategoryCard(
    item: ModelItemCard,
    onClick: (String) -> Unit
) {
    val categoryColor = getCategoryColor(item.idCategory)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(item.title) },
        shape = RoundedCornerShape(
            bottomStart = 8.dp,
            topEnd = 8.dp,
            bottomEnd = 8.dp,
            topStart = 8.dp
        ),
        colors = CardDefaults.cardColors(containerColor = categoryColor),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = (item.idCard).toString(),
                fontSize = dimensionResource(id = R.dimen.size_text_card).value.sp,
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.margin_8dp),
                        vertical = dimensionResource(id = R.dimen.margin_8dp)
                    )
            )
            Text(
                text = (item.title),
                fontSize = dimensionResource(id = R.dimen.size_title_card).value.sp,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margin_8dp))
            )
        }
    }
}