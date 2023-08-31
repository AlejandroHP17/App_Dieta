package com.liftechnology.planalimenticio.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "food_table")
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "categoria") val categoria: String,
    val alimento : String,
    val cantidad_sugerida : Float,
    val unidad : String,
    val peso_neto_g: String,
    val peso_bruto_redondeado_g : Int,
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
    val azucar_por_equivalente_g : Float?,
    val calcio_mg: Float?,
    val hierro_mg :Float?,
    val sodio_mg :Float?,
    val colesterol_mg :Float?,
    val selenio_mg :Float?,
    val selenio_ug :Float?,
    val fosforo_mg :Float?,
    val ag_saturados_g :Float?,
    val ag_monoinsaturados_g :Float?,
    val ag_poliinsaturados_g :Float?,
) : Parcelable