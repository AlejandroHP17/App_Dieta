package com.liftechnology.planalimenticio.main.components.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.main.components.common.getCategoryColor
import com.liftechnology.planalimenticio.main.theme.colorWhite
import com.liftechnology.planalimenticio.model.ModelSubItemCard
import com.liftechnology.planalimenticio.model.toNonNullList


@Preview(showBackground = true)
@Composable
private fun FoodCardView() {
    val data = ModelSubItemCard(
        idCard = 1,
        idCategory = 1,
        foodTitle = "Acelga cruda",
        suggestedQuantity = "2 taza",
        netWeightG = "98 g",
        roundedGrossWeightG = "120 g",
        energyKcal = "22 kcal",
        proteinG = "2.2 g",
        lipidsG = "0.1 g",
        carbohydratesG = "4.3 g",
        fiverG = "3.6 g",
        vitaminAUgRe = "310.9 µg RE",
        ascorbicAcidMg = "29.5 mg",
        folicAcidUg = "14.8 µg",
        ironNoHemMg = "2.5 mg",
        potassiumMg = "749.8 mg",
        hypoglycemicIndex = "64",
        hypoglycemicLoad = "2.7",
        sugarPerEquivalentG = null,
        calciumMg = null,
        ironMg = null,
        sodiumMg = null,
        cholesterolMg = null,
        seleniumMg = null,
        phosphorusMg = null,
        agSaturatedG = null,
        agMonounsaturatedG = null,
        agPolyunsaturatedG = null
    )
    FoodCard(item = data)
}

@Composable
fun FoodCard(
    item: ModelSubItemCard
) {
    val isExpandable = remember { mutableStateOf(false) }
    val categoryColor = getCategoryColor(item.idCategory)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpandable.value = !isExpandable.value },
        shape = RoundedCornerShape(
            bottomStart = 8.dp,
            topEnd = 8.dp,
            bottomEnd = 8.dp,
            topStart = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = categoryColor
        ),
        border = BorderStroke(
            width = 2.dp,
            color = categoryColor
        )
    ) {
        Column{
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                /*Text(
                    text = (item.idCard).toString(),
                    fontSize = dimensionResource(id = R.dimen.size_title_card).value.sp,
                    modifier = Modifier
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.margin_8dp),
                            vertical = dimensionResource(id = R.dimen.margin_8dp)
                        )
                )*/
                Text(
                    text = (item.foodTitle),
                    fontSize = dimensionResource(id = R.dimen.size_text_button).value.sp,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.margin_8dp))
                        .weight(1f)
                )

                Text(
                    text = (item.netWeightG),
                    fontSize = dimensionResource(id = R.dimen.size_text_button).value.sp,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.margin_8dp)),
                    maxLines = 1 // Evita que este texto dé saltos de línea

                )
            }

            if(isExpandable.value) DetailFoodCard(item)
        }

    }
}

@Composable
private fun DetailFoodCard(
    item: ModelSubItemCard
){
    Column(
        modifier = Modifier
            .background(colorWhite)
            .fillMaxWidth()
            .padding(4.dp)
    ){
        val itemsList = item.toNonNullList()
        itemsList.forEach { itemText: String ->
            Text(
                text = itemText,
                fontSize = dimensionResource(id = R.dimen.size_title_card).value.sp,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margin_8dp))
            )
        }
    }
}