package com.liftechnology.planalimenticio.data.local.entity

/**
 * Enum que define todas las unidades de medida utilizadas en FoodEntity.
 * 
 * @author pelkidev
 */
enum class FoodUnit(val symbol: String, val displayName: String) {
    GRAMS("g", "gramos"),
    KILOCALORIES("kcal", "kilocalorías"),
    MILLIGRAMS("mg", "miligramos"),
    MICROGRAMS_RE("µg RE", "microgramos de retinol equivalente"),
    MICROGRAMS("µg", "microgramos"),
    UNITLESS("", "sin unidad"),
    CUP("taza", "taza"),
    PIECE("pieza", "pieza"),
    SLICE("rebanada", "rebanada"),
    TABLESPOON("cucharada", "cucharada"),
    TEASPOON("cucharadita", "cucharadita")
}

/**
 * Objeto que contiene los metadatos de cada campo de FoodEntity,
 * incluyendo su unidad de medida y etiqueta para mostrar.
 * 
 * @author pelkidev
 */
object FoodEntityMetadata {
    /**
     * Obtiene la unidad de medida para un campo específico.
     */
    fun getUnit(fieldName: String): FoodUnit {
        return when (fieldName) {
            "suggestedQuantity" -> FoodUnit.UNITLESS
            "unit" -> FoodUnit.UNITLESS
            "netWeightG" -> FoodUnit.GRAMS
            "roundedGrossWeightG" -> FoodUnit.GRAMS
            "energyKcal" -> FoodUnit.KILOCALORIES
            "proteinG" -> FoodUnit.GRAMS
            "lipidsG" -> FoodUnit.GRAMS
            "carbohydratesG" -> FoodUnit.GRAMS
            "fiverG" -> FoodUnit.GRAMS
            "vitaminAUgRe" -> FoodUnit.MICROGRAMS_RE
            "ascorbicAcidMg" -> FoodUnit.MILLIGRAMS
            "folicAcidUg" -> FoodUnit.MICROGRAMS
            "ironNoHemMg" -> FoodUnit.MILLIGRAMS
            "potassiumMg" -> FoodUnit.MILLIGRAMS
            "hypoglycemicIndex" -> FoodUnit.UNITLESS
            "hypoglycemicLoad" -> FoodUnit.UNITLESS
            "sugarPerEquivalentG" -> FoodUnit.GRAMS
            "calciumMg" -> FoodUnit.MILLIGRAMS
            "ironMg" -> FoodUnit.MILLIGRAMS
            "sodiumMg" -> FoodUnit.MILLIGRAMS
            "cholesterolMg" -> FoodUnit.MILLIGRAMS
            "seleniumMg" -> FoodUnit.MILLIGRAMS
            "phosphorusMg" -> FoodUnit.MILLIGRAMS
            "agSaturatedG" -> FoodUnit.GRAMS
            "agMonounsaturatedG" -> FoodUnit.GRAMS
            "agPolyunsaturatedG" -> FoodUnit.GRAMS
            else -> FoodUnit.UNITLESS
        }
    }
    
    /**
     * Obtiene la etiqueta de visualización para un campo específico.
     */
    fun getLabel(fieldName: String): String {
        return when (fieldName) {
            "suggestedQuantity" -> "Cantidad sugerida"
            "unit" -> "Unidad"
            "netWeightG" -> "Peso neto"
            "roundedGrossWeightG" -> "Peso bruto redondeado"
            "energyKcal" -> "Energía"
            "proteinG" -> "Proteína"
            "lipidsG" -> "Lípidos"
            "carbohydratesG" -> "Hidratos de carbono"
            "fiverG" -> "Fibra"
            "vitaminAUgRe" -> "Vitamina A"
            "ascorbicAcidMg" -> "Ácido Ascórbico"
            "folicAcidUg" -> "Ácido Fólico"
            "ironNoHemMg" -> "Hierro NO HEM"
            "potassiumMg" -> "Potasio"
            "hypoglycemicIndex" -> "Índice glicémico"
            "hypoglycemicLoad" -> "Carga glicémica"
            "sugarPerEquivalentG" -> "Azúcar por equivalente"
            "calciumMg" -> "Calcio"
            "ironMg" -> "Hierro"
            "sodiumMg" -> "Sodio"
            "cholesterolMg" -> "Colesterol"
            "seleniumMg" -> "Selenio"
            "phosphorusMg" -> "Fósforo"
            "agSaturatedG" -> "AG Saturados"
            "agMonounsaturatedG" -> "AG Monoinsaturados"
            "agPolyunsaturatedG" -> "AG Poliinsaturados"
            else -> fieldName
        }
    }
}

/**
 * Formatea un valor con su unidad de medida.
 */
private fun formatWithUnit(value: Any?, unit: FoodUnit): String {
    if (value == null) return "N/A"
    return when (value) {
        is Float -> {
            if (value % 1.0f == 0f) {
                "${value.toInt()} ${unit.symbol}".trim()
            } else {
                String.format("%.2f %s", value, unit.symbol).trim()
            }
        }
        is Double -> {
            if (value % 1.0 == 0.0) {
                "${value.toInt()} ${unit.symbol}".trim()
            } else {
                String.format("%.2f %s", value, unit.symbol).trim()
            }
        }
        is Int -> "${value} ${unit.symbol}".trim()
        is String -> if (value.isBlank() || value == "0") "N/A" else "$value ${unit.symbol}".trim()
        else -> "$value ${unit.symbol}".trim()
    }
}

/**
 * Funciones de extensión para FoodEntity que proporcionan acceso fácil
 * a las unidades y formateo de valores.
 * 
 * @author pelkidev
 */

/**
 * Obtiene el valor formateado de netWeightG con su unidad.
 */
fun FoodEntity.getNetWeightFormatted(): String {
    return formatWithUnit(netWeightG, FoodUnit.GRAMS)
}

/**
 * Obtiene el valor formateado de energyKcal con su unidad.
 */
fun FoodEntity.getEnergyFormatted(): String {
    return formatWithUnit(energyKcal, FoodUnit.KILOCALORIES)
}

/**
 * Obtiene el valor formateado de proteinG con su unidad.
 */
fun FoodEntity.getProteinFormatted(): String {
    return formatWithUnit(proteinG, FoodUnit.GRAMS)
}

/**
 * Obtiene el valor formateado de roundedGrossWeightG con su unidad.
 */
fun FoodEntity.getRoundedGrossWeightFormatted(): String {
    return formatWithUnit(roundedGrossWeightG, FoodUnit.GRAMS)
}

/**
 * Obtiene el valor formateado de lipidsG con su unidad.
 */
fun FoodEntity.getLipidsFormatted(): String {
    return formatWithUnit(lipidsG, FoodUnit.GRAMS)
}

/**
 * Obtiene el valor formateado de carbohydratesG con su unidad.
 */
fun FoodEntity.getCarbohydratesFormatted(): String {
    return formatWithUnit(carbohydratesG, FoodUnit.GRAMS)
}

/**
 * Obtiene el valor formateado de fiverG con su unidad.
 */
fun FoodEntity.getFiberFormatted(): String {
    return formatWithUnit(fiverG, FoodUnit.GRAMS)
}

/**
 * Obtiene el valor formateado de vitaminAUgRe con su unidad.
 */
fun FoodEntity.getVitaminAFormatted(): String {
    return formatWithUnit(vitaminAUgRe, FoodUnit.MICROGRAMS_RE)
}

/**
 * Obtiene el valor formateado de ascorbicAcidMg con su unidad.
 */
fun FoodEntity.getAscorbicAcidFormatted(): String {
    return formatWithUnit(ascorbicAcidMg, FoodUnit.MILLIGRAMS)
}

/**
 * Obtiene el valor formateado de folicAcidUg con su unidad.
 */
fun FoodEntity.getFolicAcidFormatted(): String {
    return formatWithUnit(folicAcidUg, FoodUnit.MICROGRAMS)
}

/**
 * Obtiene el valor formateado de ironNoHemMg con su unidad.
 */
fun FoodEntity.getIronNoHemFormatted(): String {
    return formatWithUnit(ironNoHemMg, FoodUnit.MILLIGRAMS)
}

/**
 * Obtiene el valor formateado de potassiumMg con su unidad.
 */
fun FoodEntity.getPotassiumFormatted(): String {
    return formatWithUnit(potassiumMg, FoodUnit.MILLIGRAMS)
}

/**
 * Obtiene el valor formateado de calciumMg con su unidad.
 */
fun FoodEntity.getCalciumFormatted(): String {
    return formatWithUnit(calciumMg, FoodUnit.MILLIGRAMS)
}

/**
 * Obtiene el valor formateado de ironMg con su unidad.
 */
fun FoodEntity.getIronFormatted(): String {
    return formatWithUnit(ironMg, FoodUnit.MILLIGRAMS)
}

/**
 * Obtiene el valor formateado de sodiumMg con su unidad.
 */
fun FoodEntity.getSodiumFormatted(): String {
    return formatWithUnit(sodiumMg, FoodUnit.MILLIGRAMS)
}

/**
 * Obtiene el valor formateado de cholesterolMg con su unidad.
 */
fun FoodEntity.getCholesterolFormatted(): String {
    return formatWithUnit(cholesterolMg, FoodUnit.MILLIGRAMS)
}

/**
 * Obtiene el valor formateado de seleniumMg con su unidad.
 */
fun FoodEntity.getSeleniumFormatted(): String {
    return formatWithUnit(seleniumMg, FoodUnit.MILLIGRAMS)
}

/**
 * Obtiene el valor formateado de phosphorusMg con su unidad.
 */
fun FoodEntity.getPhosphorusFormatted(): String {
    return formatWithUnit(phosphorusMg, FoodUnit.MILLIGRAMS)
}

/**
 * Obtiene el valor formateado de agSaturatedG con su unidad.
 */
fun FoodEntity.getAgSaturatedFormatted(): String {
    return formatWithUnit(agSaturatedG, FoodUnit.GRAMS)
}

/**
 * Obtiene el valor formateado de agMonounsaturatedG con su unidad.
 */
fun FoodEntity.getAgMonounsaturatedFormatted(): String {
    return formatWithUnit(agMonounsaturatedG, FoodUnit.GRAMS)
}

/**
 * Obtiene el valor formateado de agPolyunsaturatedG con su unidad.
 */
fun FoodEntity.getAgPolyunsaturatedFormatted(): String {
    return formatWithUnit(agPolyunsaturatedG, FoodUnit.GRAMS)
}

/**
 * Obtiene el valor formateado de sugarPerEquivalentG con su unidad.
 */
fun FoodEntity.getSugarPerEquivalentFormatted(): String {
    return formatWithUnit(sugarPerEquivalentG, FoodUnit.GRAMS)
}

/**
 * Obtiene el valor formateado de hypoglycemicIndex con su unidad.
 */
fun FoodEntity.getHypoglycemicIndexFormatted(): String {
    return formatWithUnit(hypoglycemicIndex, FoodUnit.UNITLESS)
}

/**
 * Obtiene el valor formateado de hypoglycemicLoad con su unidad.
 */
fun FoodEntity.getHypoglycemicLoadFormatted(): String {
    return formatWithUnit(hypoglycemicLoad, FoodUnit.UNITLESS)
}

/**
 * Obtiene el valor formateado de suggestedQuantity con su unidad.
 */
fun FoodEntity.getSuggestedQuantityFormatted(): String {
    val quantity = if (suggestedQuantity % 1.0f == 0f) {
        suggestedQuantity.toInt().toString()
    } else {
        String.format("%.2f", suggestedQuantity)
    }
    return "$quantity $unit".trim()
}

