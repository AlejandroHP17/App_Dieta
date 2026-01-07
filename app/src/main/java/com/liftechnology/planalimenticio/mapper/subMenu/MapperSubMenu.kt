package com.liftechnology.planalimenticio.mapper.subMenu

import com.liftechnology.planalimenticio.data.local.entity.FoodEntity
import com.liftechnology.planalimenticio.data.local.entity.getAgMonounsaturatedFormatted
import com.liftechnology.planalimenticio.data.local.entity.getAgPolyunsaturatedFormatted
import com.liftechnology.planalimenticio.data.local.entity.getAgSaturatedFormatted
import com.liftechnology.planalimenticio.data.local.entity.getAscorbicAcidFormatted
import com.liftechnology.planalimenticio.data.local.entity.getCalciumFormatted
import com.liftechnology.planalimenticio.data.local.entity.getCarbohydratesFormatted
import com.liftechnology.planalimenticio.data.local.entity.getCholesterolFormatted
import com.liftechnology.planalimenticio.data.local.entity.getEnergyFormatted
import com.liftechnology.planalimenticio.data.local.entity.getFiberFormatted
import com.liftechnology.planalimenticio.data.local.entity.getFolicAcidFormatted
import com.liftechnology.planalimenticio.data.local.entity.getHypoglycemicIndexFormatted
import com.liftechnology.planalimenticio.data.local.entity.getHypoglycemicLoadFormatted
import com.liftechnology.planalimenticio.data.local.entity.getIronFormatted
import com.liftechnology.planalimenticio.data.local.entity.getIronNoHemFormatted
import com.liftechnology.planalimenticio.data.local.entity.getLipidsFormatted
import com.liftechnology.planalimenticio.data.local.entity.getNetWeightFormatted
import com.liftechnology.planalimenticio.data.local.entity.getPhosphorusFormatted
import com.liftechnology.planalimenticio.data.local.entity.getPotassiumFormatted
import com.liftechnology.planalimenticio.data.local.entity.getProteinFormatted
import com.liftechnology.planalimenticio.data.local.entity.getRoundedGrossWeightFormatted
import com.liftechnology.planalimenticio.data.local.entity.getSeleniumFormatted
import com.liftechnology.planalimenticio.data.local.entity.getSodiumFormatted
import com.liftechnology.planalimenticio.data.local.entity.getSugarPerEquivalentFormatted
import com.liftechnology.planalimenticio.data.local.entity.getVitaminAFormatted
import com.liftechnology.planalimenticio.model.ModelSubItemCard

fun List<FoodEntity>.toSubMenuMapper(flag: Boolean): List<ModelSubItemCard>? {
    return this.mapIndexed { pos, it ->
        ModelSubItemCard(
            idCard = it.id, // Usar el ID único de la entidad en lugar de pos + 1 o idCategory
            idCategory = it.idCategory,
            foodTitle = it.food,
            suggestedQuantity = "${it.suggestedQuantity} ${it.unit}",
            netWeightG = it.getNetWeightFormatted(),
            roundedGrossWeightG = it.getRoundedGrossWeightFormatted(),
            energyKcal = it.getEnergyFormatted(),
            proteinG = it.getProteinFormatted(),
            lipidsG = it.getLipidsFormatted(),
            carbohydratesG = it.getCarbohydratesFormatted(),
            fiverG = it.getFiberFormatted(),
            vitaminAUgRe = it.getVitaminAFormatted(),
            ascorbicAcidMg = it.getAscorbicAcidFormatted(),
            folicAcidUg = it.getFolicAcidFormatted(),
            ironNoHemMg = it.getIronNoHemFormatted(),
            potassiumMg = it.getPotassiumFormatted(),
            hypoglycemicIndex = it.getHypoglycemicIndexFormatted(),
            hypoglycemicLoad = it.getHypoglycemicLoadFormatted(),
            sugarPerEquivalentG = it.getSugarPerEquivalentFormatted(),
            calciumMg = it.getCalciumFormatted(),
            ironMg = it.getIronFormatted(),
            sodiumMg = it.getSodiumFormatted(),
            cholesterolMg = it.getCholesterolFormatted(),
            seleniumMg = it.getSeleniumFormatted(),
            phosphorusMg = it.getPhosphorusFormatted(),
            agSaturatedG = it.getAgSaturatedFormatted(),
            agMonounsaturatedG = it.getAgMonounsaturatedFormatted(),
            agPolyunsaturatedG = it.getAgPolyunsaturatedFormatted()
        )
    }
}