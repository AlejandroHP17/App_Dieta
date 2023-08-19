package com.liftechnology.planalimenticio.data.network.models.response

class GenericResponse <T>(
    val code: Int,
    val message: String,
    val result: List<T>
)