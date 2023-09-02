package com.liftechnology.planalimenticio.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.liftechnology.planalimenticio.data.local.entity.FoodEntity

@Dao
interface FoodDao {

    @Query("SELECT * FROM food_table WHERE category = :category")
    fun getFoodCategory(category:String) : List<FoodEntity>

    @Insert
    fun insertAllListFood(listFood:List<FoodEntity>)
}