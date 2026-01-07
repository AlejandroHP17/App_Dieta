package com.liftechnology.planalimenticio.main.components.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.liftechnology.planalimenticio.main.theme.colorBgCard

/**
 * Obtiene el color de la tarjeta según el ID de categoría.
 * Cada categoría tiene un color único para facilitar la identificación visual.
 *
 * @param idCategory ID de la categoría (1-17)
 * @return Color correspondiente a la categoría
 */
@Composable
fun getCategoryColor(idCategory: Int): Color {
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