package com.liftechnology.planalimenticio.main.utils.regex

object ModelRegex {
    /** Expresión regular para texto simple (letras y espacios). */
    val SIMPLE_TEXT = Regex("^[A-Za-z \\-áéíóúÁÉÍÓÚñÑ]+$")
    val PRIMITIVE_TEXT = Regex("[^0-9.\\-]")
}