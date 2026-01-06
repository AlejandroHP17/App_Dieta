package com.liftechnology.planalimenticio.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "food_table",
    indices = [
        Index(value = ["category"]),  // Índice para búsquedas por categoría
        Index(value = ["food"])       // Índice para búsquedas por nombre
    ]
)
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1,
    @ColumnInfo(name = "category") val category: String,
    val food : String,
    val suggestedQuantity : Float,
    val unit : String,
    val netWeightG: String,
    val roundedGrossWeightG : Int,
    val energyKcal : Int,
    val proteinG : Float?,
    val lipidsG :Float?,
    val carbohydratesG :Float?,
    val fiverG :Float?,
    val vitaminAUgRe :Float?,
    val ascorbicAcidMg : Float?,
    val folicAcidUg : Float?,
    val ironNoHemMg : Float?,
    val potassiumMg : Float?,
    val hypoglycemicIndex : Float?,
    val hypoglycemicLoad :Float?,
    val sugarPerEquivalentG : Float?,
    val calciumMg: Float?,
    val ironMg :Float?,
    val sodiumMg :Float?,
    val cholesterolMg :Float?,
    val seleniumMg :Float?,
    val phosphorusMg :Float?,
    val agSaturatedG :Float?,
    val agMonounsaturatedG :Float?,
    val agPolyunsaturatedG :Float?,
) : Parcelable
