package com.liftechnology.planalimenticio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.data.network.models.response.FoodResponse
import com.liftechnology.planalimenticio.data.network.models.response.local.ModelCardList
import com.liftechnology.planalimenticio.framework.CoroutineScopeManager
import com.liftechnology.planalimenticio.model.usecase.FoodUseCase
import com.liftechnology.planalimenticio.ui.utils.ErrorCode
import kotlinx.coroutines.launch

class VMCategory(
    private val useCase: FoodUseCase
) : ViewModel() {
    // Corrutina controlada
    private val coroutine = CoroutineScopeManager()

    private val _listFood = MutableLiveData<List<ModelCardList>>()
    val listFood: LiveData<List<ModelCardList>> = _listFood

    private val _errorListFood = MutableLiveData<String>()
    val errorListFood: LiveData<String> = _errorListFood

    fun getListFood(data: CategoryResponse?) {
        coroutine.scopeIO.launch {
            try {
                useCase.getListFood(data?.url, data?.category) { success, error ->
                    if (error.isNullOrEmpty()) {
                        buildListFood(success, data?.startColor, data?.endColor)
                    } else {
                        _errorListFood.postValue(error)
                    }
                }
            } catch (e: java.lang.Exception) {
                _errorListFood.postValue(ErrorCode.ERROR_APP)
            }
        }
    }

    private fun buildListFood(items: List<FoodResponse>?, startColor: String?, endColor: String?) {
        val list: MutableList<ModelCardList> = mutableListOf()
        items?.forEach {
            list.add(
                ModelCardList(
                    food = it.food,
                    suggestedQuantity = it.suggestedQuantity,
                    unit = it.unit,
                    netWeightG = it.netWeightG,
                    startColor = startColor ?: "#FFFFFF",
                    endColor = endColor?:"#FFFFFF",
                    roundedGrossWeightG = it.roundedGrossWeightG,
                    energyKcal = it.energyKcal,
                    proteinG = it.proteinG,
                    lipidsG = it.lipidsG,
                    carbohydratesG = it.carbohydratesG,
                    fiverG = it.fiverG,
                    vitaminAUgRe = it.vitaminAUgRe,
                    ascorbicAcidMg = it.ascorbicAcidMg,
                    folicAcidUg = it.folicAcidUg,
                    ironNoHemMg = it.ironNoHemMg,
                    potassiumMg = it.potassiumMg,
                    hypoglycemicIndex = it.hypoglycemicIndex,
                    hypoglycemicLoad = it.hypoglycemicLoad,
                    sugarPerEquivalentG = it.sugarPerEquivalentG,
                    calciumMg = it.calciumMg,
                    ironMg = it.ironMg,
                    sodiumMg = it.sodiumMg,
                    cholesterolMg = it.cholesterolMg,
                    seleniumMg = it.seleniumMg,
                    seleniumUg = it.seleniumUg,
                    phosphorusMg = it.phosphorusMg,
                    agSaturatedG = it.agSaturatedG,
                    agMonounsaturatedG = it.agMonounsaturatedG,
                    agPolyunsaturatedG = it.agPolyunsaturatedG,
                    category = it.category
                )
            )
        }

        val listFood: MutableList<ModelCardList> = mutableListOf()
        listFood.addAll(list)
        _listFood.postValue(listFood)
    }

    fun buildTextAlert(modelCardList: ModelCardList): StringBuilder {
        val sequenceString: StringBuilder = StringBuilder()
        with(modelCardList) {
            suggestedQuantity.let { sequenceString.append("Cantidad sugerida: ").append(it).append("\n") }
            unit.let { sequenceString.append("unit: ").append(it).append("\n") }
            netWeightG.let { sequenceString.append("Peso neto: ").append(it).append(" g").append("\n") }
            netWeightG.let {
                sequenceString.append("Peso bruto redondeado: ").append(it).append(" g")
                    .append("\n")
            }
            energyKcal.let {
                sequenceString.append("Energía: ").append(it.toString()).append(" Kcal").append("\n")
            }
            proteinG?.let {
                sequenceString.append("Proteína: ").append(it.toString()).append(" g").append("\n")
            }
            lipidsG?.let {
                sequenceString.append("Lípidos: ").append(it.toString()).append(" g").append("\n")
            }
            carbohydratesG?.let {
                sequenceString.append("Hidratos de carbono: ").append(it.toString()).append(" g")
                    .append("\n")
            }
            fiverG?.let {
                sequenceString.append("Fibra: ").append(it.toString()).append(" g").append("\n")
            }
            vitaminAUgRe?.let {
                sequenceString.append("Vitamina A ug re: ").append(it.toString()).append("\n")
            }
            ascorbicAcidMg?.let {
                sequenceString.append("Ácido ascórbico: ").append(it.toString()).append(" mg").append("\n")
            }
            folicAcidUg?.let {
                sequenceString.append("Ácido fólico: ").append(it.toString()).append(" ug").append("\n")
            }
            ironNoHemMg?.let {
                sequenceString.append("Hiero no hem: ").append(it.toString()).append(" mg").append("\n")
            }
            potassiumMg?.let {
                sequenceString.append("Potasio: ").append(it.toString()).append(" mg").append("\n")
            }
            hypoglycemicIndex?.let {
                sequenceString.append("Índice glicémico: ").append(it.toString()).append("\n")
            }
            hypoglycemicLoad?.let {
                sequenceString.append("Carga glicémica: ").append(it.toString()).append("\n")
            }
            sugarPerEquivalentG?.let {
                sequenceString.append("Azúcar por equivalente: ").append(it.toString()).append(" g")
                    .append("\n")
            }
            calciumMg?.let {
                sequenceString.append("Calcio: ").append(it.toString()).append(" mg").append("\n")
            }
            ironMg?.let {
                sequenceString.append("Hierro: ").append(it.toString()).append(" mg").append("\n")
            }
            sodiumMg?.let {
                sequenceString.append("Sodio: ").append(it.toString()).append(" mg").append("\n")
            }
            cholesterolMg?.let {
                sequenceString.append("Colesterol: ").append(it.toString()).append(" mg").append("\n")
            }
            seleniumMg?.let {
                sequenceString.append("Selenio: ").append(it.toString()).append(" mg").append("\n")
            }
            seleniumMg?.let {
                sequenceString.append("Selenio: ").append(it.toString()).append(" ug").append("\n")
            }
            phosphorusMg?.let {
                sequenceString.append("Fósforo: ").append(it.toString()).append(" mg").append("\n")
            }
            agSaturatedG?.let {
                sequenceString.append("AG Saturados: ").append(it.toString()).append(" g").append("\n")
            }
            agMonounsaturatedG?.let {
                sequenceString.append("AG Monoinsaturados: ").append(it.toString()).append(" g")
                    .append("\n")
            }
            agPolyunsaturatedG?.let {
                sequenceString.append("AG Polinsaturados: ").append(it.toString()).append(" g").append("\n")
            }
        }

        return sequenceString
    }

}