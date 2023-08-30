package com.liftechnology.planalimenticio.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.liftechnology.planalimenticio.data.local.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category_table ORDER BY id ASC")
    fun getAllCategory(): List<CategoryEntity>

    @Insert
    fun insertAllCategory(category:List<CategoryEntity>)
}