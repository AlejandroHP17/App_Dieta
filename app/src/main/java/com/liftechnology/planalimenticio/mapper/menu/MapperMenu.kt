package com.liftechnology.planalimenticio.mapper.menu

import com.liftechnology.planalimenticio.model.ui.ModelItemCard

/**
 * Mapeo de nombres de categorías a sus IDs correspondientes.
 * Basado en el orden y nombres de las categorías en el JSON.
 */
private val categoryToIdMap = mapOf(
    "Aceite y grasas con proteína" to 1,
    "Aceites y grasas" to 2,
    "Alimentos de origen animal alto aporte de grasa" to 3,
    "Alimentos de origen animal bajo aporte de grasa" to 4,
    "Alimentos de origen animal moderado aporte de grasa" to 5,
    "Alimentos de origen animal muy bajo aporte de grasa" to 6,
    "Azúcares con grasa" to 7,
    "Azúcares sin grasa" to 8,
    "Cereales con grasa" to 9,
    "Cereales sin grasa" to 10,
    "Frutas" to 11,
    "Leche con azúcar" to 12,
    "Leche descremada" to 13,
    "Leche entera" to 14,
    "Leche semidescremada" to 15,
    "Leguminosas" to 16,
    "Verduras" to 17
)

fun List<String>.toMenuMapper(): List<ModelItemCard> {
    return this.mapIndexed { pos, categoryName ->
        ModelItemCard(
            idCard = pos + 1,
            idCategory = categoryToIdMap[categoryName] ?: (pos + 1), // Usa el mapeo o el índice como fallback
            title = categoryName
        )
    }
}