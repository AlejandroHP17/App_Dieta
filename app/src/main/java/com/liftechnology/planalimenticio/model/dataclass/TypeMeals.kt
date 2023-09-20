package com.liftechnology.planalimenticio.model.dataclass

import java.io.Serializable

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

data class TypeTable(
    val list: List<TypeMeals>,
    val meals: Pair<String,Int>? = null
): Serializable