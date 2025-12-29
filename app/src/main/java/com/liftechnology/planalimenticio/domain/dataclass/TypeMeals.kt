package com.liftechnology.planalimenticio.domain.dataclass

import java.io.Serializable

/** Modelo para construir la tabla en el recycler
 * @author pelkidev
 * @date 01/09/2023
 * */
data class TypeMeals(
    val category: Pair<String,Int>? = null,
    val meal1: Pair<String,Int>? = null,
    val meal2: Pair<String,Int>? = null,
    val meal3: Pair<String,Int>? = null,
    val meal4: Pair<String,Int>? = null,
    val meal5: Pair<String,Int>? = null,
    val meal6: Pair<String,Int>? = null,
    val meal7: Pair<String,Int>? = null
)

/** Modelo para tabla completa
 * @author pelkidev
 * @date 01/09/2023
 * */
data class TypeTable(
    val list: List<TypeMeals>,
    val meals: Pair<String,Int>? = null
): Serializable

data class ListTypeTable(
    val typeTable: TypeTable,
    val name : String
): Serializable