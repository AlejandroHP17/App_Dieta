package com.liftechnology.planalimenticio.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "category_table")

data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    @ColumnInfo(name = "category") val category: String,
    val url: String,
    val startColor : String,
    val endColor : String
):Parcelable
