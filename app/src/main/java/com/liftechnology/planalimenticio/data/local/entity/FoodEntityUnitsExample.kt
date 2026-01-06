package com.liftechnology.planalimenticio.data.local.entity

/**
 * Ejemplos de uso del sistema de unidades para FoodEntity.
 * 
 * Este archivo muestra cómo usar las funciones de extensión y metadatos
 * para acceder a las unidades de medida de cada campo.
 * 
 * @author pelkidev
 */

// EJEMPLO 1: Usar funciones de extensión para formatear valores con unidades
fun example1_UsingExtensionFunctions(food: FoodEntity) {
    // Obtener valores formateados con sus unidades
    val netWeight = food.getNetWeightFormatted()        // "98 g"
    val energy = food.getEnergyFormatted()              // "22 kcal"
    val protein = food.getProteinFormatted()            // "2.2 g"
    val vitaminA = food.getVitaminAFormatted()          // "310.9 µg RE"
    val calcium = food.getCalciumFormatted()            // "N/A" o "45.2 mg"
    
    println("Peso neto: $netWeight")
    println("Energía: $energy")
    println("Proteína: $protein")
    println("Vitamina A: $vitaminA")
    println("Calcio: $calcium")
}

// EJEMPLO 2: Obtener la unidad de un campo específico
fun example2_GetUnitForField(fieldName: String) {
    val unit = FoodEntityMetadata.getUnit(fieldName)
    println("Unidad para '$fieldName': ${unit.symbol} (${unit.displayName})")
    
    // Uso:
    // example2_GetUnitForField("proteinG")  // "g (gramos)"
    // example2_GetUnitForField("energyKcal") // "kcal (kilocalorías)"
}

// EJEMPLO 3: Obtener la etiqueta de visualización de un campo
fun example3_GetLabelForField(fieldName: String) {
    val label = FoodEntityMetadata.getLabel(fieldName)
    println("Etiqueta para '$fieldName': $label")
    
    // Uso:
    // example3_GetLabelForField("proteinG")  // "Proteína"
    // example3_GetLabelForField("energyKcal") // "Energía"
}

// EJEMPLO 4: Crear una representación completa de un campo
fun example4_CompleteFieldRepresentation(food: FoodEntity, fieldName: String) {
    val label = FoodEntityMetadata.getLabel(fieldName)
    val unit = FoodEntityMetadata.getUnit(fieldName)
    
    // Obtener el valor según el campo
    val value = when (fieldName) {
        "netWeightG" -> food.netWeightG
        "energyKcal" -> food.energyKcal.toString()
        "proteinG" -> food.proteinG?.toString() ?: "N/A"
        "vitaminAUgRe" -> food.vitaminAUgRe?.toString() ?: "N/A"
        else -> "N/A"
    }
    
    val formatted = "$label: $value ${unit.symbol}"
    println(formatted)
    // Ejemplo de salida: "Proteína: 2.2 g"
}

// EJEMPLO 5: Iterar sobre todos los campos con unidades
fun example5_IterateAllFields(food: FoodEntity) {
    val fields = listOf(
        "netWeightG" to food.netWeightG,
        "energyKcal" to food.energyKcal,
        "proteinG" to food.proteinG,
        "lipidsG" to food.lipidsG,
        "carbohydratesG" to food.carbohydratesG,
        "fiverG" to food.fiverG,
        "vitaminAUgRe" to food.vitaminAUgRe,
        "ascorbicAcidMg" to food.ascorbicAcidMg,
        "calciumMg" to food.calciumMg,
        "ironMg" to food.ironMg,
        "sodiumMg" to food.sodiumMg
    )
    
    fields.forEach { (fieldName, value) ->
        if (value != null) {
            val label = FoodEntityMetadata.getLabel(fieldName)
            val unit = FoodEntityMetadata.getUnit(fieldName)
            println("$label: $value ${unit.symbol}")
        }
    }
}

// EJEMPLO 6: Usar en Compose para mostrar información nutricional
/*
@Composable
fun NutritionalInfoCard(food: FoodEntity) {
    Column {
        Text("${food.food}")
        Text("Peso: ${food.getNetWeightFormatted()}")
        Text("Energía: ${food.getEnergyFormatted()}")
        Text("Proteína: ${food.getProteinFormatted()}")
        Text("Lípidos: ${food.getLipidsFormatted()}")
        Text("Carbohidratos: ${food.getCarbohydratesFormatted()}")
        Text("Fibra: ${food.getFiberFormatted()}")
    }
}
*/

