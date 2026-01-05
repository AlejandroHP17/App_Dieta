package com.liftechnology.planalimenticio.mapper.menu

import com.liftechnology.planalimenticio.model.ui.ModelItemCard

fun List<String>.toMenuMapper(): List<ModelItemCard>? {
    return this.mapIndexed { pos, it ->
        ModelItemCard(
            idCard = pos + 1,
            title = it
        )
    }
}