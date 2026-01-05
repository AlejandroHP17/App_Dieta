package com.liftechnology.planalimenticio.mapper.subMenu

import com.liftechnology.planalimenticio.data.local.entity.FoodEntity
import com.liftechnology.planalimenticio.model.ui.ModelItemCard

fun List<FoodEntity>.toSubMenuMapper(): List<ModelItemCard>? {
    return this.mapIndexed { pos, it ->
        ModelItemCard(
            idCard = pos + 1,
            title = it.food
        )
    }
}