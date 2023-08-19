package com.liftechnology.planalimenticio.data.network.models.response
import java.io.Serializable

data class PrincipalResponse(
    val code: Int,
    val message: String,
    val result: List<CategoryResponse>
): Serializable