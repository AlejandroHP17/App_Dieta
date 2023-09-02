package com.liftechnology.planalimenticio.data.network.models.response

import com.google.gson.annotations.SerializedName

class FoodResponse (
    @SerializedName("alimento")
    val food : String,
    @SerializedName("cantidad_sugerida")
    val suggestedQuantity : Float,
    @SerializedName("unidad")
    val unit : String,
    @SerializedName("peso_neto_g")
    val netWeightG: String,
    @SerializedName("peso_bruto_redondeado_g")
    val roundedGrossWeightG : Int,
    @SerializedName("energia_kcal")
    val energyKcal : Int,
    @SerializedName("proteina_g")
    val proteinG : Float?,
    @SerializedName("lipidos_g")
    val lipidsG :Float?,
    @SerializedName("hidratos_de_carbono_g")
    val carbohydratesG :Float?,
    @SerializedName("fibra_g")
    val fiverG :Float?,
    @SerializedName("vitamina_a_ug_re")
    val vitaminAUgRe :Float?,
    @SerializedName("acido_ascorbico_mg")
    val ascorbicAcidMg : Float?,
    @SerializedName("acido_folico_ug")
    val folicAcidUg : Float?,
    @SerializedName("hierro_no_hem_mg")
    val ironNoHemMg : Float?,
    @SerializedName("potasio_mg")
    val potassiumMg : Float?,
    @SerializedName("indice_glicemico")
    val hypoglycemicIndex : Float?,
    @SerializedName("carga_glicemica")
    val hypoglycemicLoad :Float?,
    @SerializedName("azucar_por_equivalente_g")
    val sugarPerEquivalentG : Float?,
    @SerializedName("calcio_mg")
    val calciumMg: Float?,
    @SerializedName("hierro_mg")
    val ironMg :Float?,
    @SerializedName("sodio_mg")
    val sodiumMg :Float?,
    @SerializedName("colesterol_mg")
    val cholesterolMg :Float?,
    @SerializedName("selenio_mg")
    val seleniumMg :Float?,
    @SerializedName("selenio_ug")
    val seleniumUg :Float?,
    @SerializedName("fosforo_mg")
    val phosphorusMg :Float?,
    @SerializedName("ag_saturados_g")
    val agSaturatedG :Float?,
    @SerializedName("ag_monoinsaturados_g")
    val agMonounsaturatedG :Float?,
    @SerializedName("ag_poliinsaturados_g")
    val agPolyunsaturatedG :Float?,
    @SerializedName("categoria")
    val category : String
)