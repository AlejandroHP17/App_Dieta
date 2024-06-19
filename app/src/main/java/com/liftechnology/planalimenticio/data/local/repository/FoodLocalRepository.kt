package com.liftechnology.planalimenticio.data.local.repository

import com.liftechnology.planalimenticio.data.local.dao.FoodDao
import com.liftechnology.planalimenticio.data.local.entity.FoodEntity

class FoodLocalRepository(private val foodDao: FoodDao) {

    fun  getCategoryFood(categoria:String?): List<FoodEntity> = foodDao.getFoodCategory(categoria)

}