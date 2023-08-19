package com.liftechnology.planalimenticio.data.network.models.response

import java.io.Serializable

data class CategoryResponse(
    val category: String,
    val url: String,
    val startColor : String,
    val endColor : String
): Serializable
