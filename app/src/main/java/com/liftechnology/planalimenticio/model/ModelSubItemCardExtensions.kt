package com.liftechnology.planalimenticio.model

import com.liftechnology.planalimenticio.data.local.entity.FoodEntityMetadata

/**
 * Función de extensión que convierte ModelSubItemCard en una lista de strings formateados.
 * Filtra los valores nulos o "N/A" y formatea cada campo como "Etiqueta: valor".
 * 
 * @return Lista de strings con los campos no nulos formateados
 * 
 * @author pelkidev
 */
fun ModelSubItemCard.toNonNullList(): List<String> {
    val items = mutableListOf<String>()
    
    // Campos que siempre se muestran (no nulos)
    items.add("${FoodEntityMetadata.getLabel("suggestedQuantity")}: $suggestedQuantity")
    items.add("${FoodEntityMetadata.getLabel("netWeightG")}: $netWeightG")
    items.add("${FoodEntityMetadata.getLabel("roundedGrossWeightG")}: $roundedGrossWeightG")
    items.add("${FoodEntityMetadata.getLabel("energyKcal")}: $energyKcal")
    
    // Campos opcionales (pueden ser nulos o "N/A")
    proteinG?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("proteinG")}: $it")
    }
    
    lipidsG?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("lipidsG")}: $it")
    }
    
    carbohydratesG?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("carbohydratesG")}: $it")
    }
    
    fiverG?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("fiverG")}: $it")
    }
    
    vitaminAUgRe?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("vitaminAUgRe")}: $it")
    }
    
    ascorbicAcidMg?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("ascorbicAcidMg")}: $it")
    }
    
    folicAcidUg?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("folicAcidUg")}: $it")
    }
    
    ironNoHemMg?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("ironNoHemMg")}: $it")
    }
    
    potassiumMg?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("potassiumMg")}: $it")
    }
    
    hypoglycemicIndex?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("hypoglycemicIndex")}: $it")
    }
    
    hypoglycemicLoad?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("hypoglycemicLoad")}: $it")
    }
    
    sugarPerEquivalentG?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("sugarPerEquivalentG")}: $it")
    }
    
    calciumMg?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("calciumMg")}: $it")
    }
    
    ironMg?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("ironMg")}: $it")
    }
    
    sodiumMg?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("sodiumMg")}: $it")
    }
    
    cholesterolMg?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("cholesterolMg")}: $it")
    }
    
    seleniumMg?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("seleniumMg")}: $it")
    }
    
    phosphorusMg?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("phosphorusMg")}: $it")
    }
    
    agSaturatedG?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("agSaturatedG")}: $it")
    }
    
    agMonounsaturatedG?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("agMonounsaturatedG")}: $it")
    }
    
    agPolyunsaturatedG?.takeIf { it != "N/A" && it.isNotBlank() }?.let {
        items.add("${FoodEntityMetadata.getLabel("agPolyunsaturatedG")}: $it")
    }
    
    return items
}

