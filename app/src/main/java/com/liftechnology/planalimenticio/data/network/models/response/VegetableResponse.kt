package com.liftechnology.planalimenticio.data.network.models.response

class VegetableResponse (
    val alimento : String,
    val cantidad_sugerida : Float,
    val unidad : String,
    val peso_bruto_redondeado_g : Int,
    val peso_neto_g : Int,
    val energia_kcal : Int,
    val proteina_g : Float?,
    val lipidos_g :Float?,
    val hidratos_de_carbono_g :Float?,
    val fibra_g :Float?,
    val vitamina_a_ug_re :Float?,
    val acido_ascorbico_mg : Float?,
    val acido_folico_ug : Float?,
    val hierro_no_hem_mg : Float?,
    val potasio_mg : Float?,
    val indice_glicemico : Float?,
    val carga_glicemica :Float?,
    val categoria : String
)