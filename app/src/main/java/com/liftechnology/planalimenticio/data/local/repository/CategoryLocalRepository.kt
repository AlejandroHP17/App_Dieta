package com.liftechnology.planalimenticio.data.local.repository

import com.liftechnology.planalimenticio.data.local.dao.CategoryDao
import com.liftechnology.planalimenticio.data.local.entity.CategoryEntity

class CategoryLocalRepository (private val categoryDao: CategoryDao){

    suspend fun getAllCategory(): List<CategoryEntity> = categoryDao.getAllCategory()

    suspend fun insertAllCategory(categories: List<CategoryEntity>) {
        categoryDao.insertAllCategory(categories)
    }

}