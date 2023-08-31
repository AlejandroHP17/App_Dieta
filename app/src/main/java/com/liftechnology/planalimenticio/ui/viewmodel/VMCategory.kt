package com.liftechnology.planalimenticio.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.liftechnology.planalimenticio.data.network.models.response.local.ModelCardList
import com.liftechnology.planalimenticio.data.network.models.response.FoodResponse
import com.liftechnology.planalimenticio.model.usecase.FoodUseCase
import com.liftechnology.planalimenticio.ui.utils.ErrorCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VMCategory (
    private val useCase: FoodUseCase
        ): ViewModel() {

    private val _listFood = MutableLiveData<List<ModelCardList>>()
    val listFood: LiveData<List<ModelCardList>> = _listFood

    private val _errorListFood = MutableLiveData<String>()
    val errorListFood : LiveData<String> = _errorListFood

    fun getListFood(url:String, startColor: String, endColor:String, category:String){

        CoroutineScope(Dispatchers.IO).launch {
            try{
                useCase.getListFood(url, category){ success, error ->
                    if (error.isNullOrEmpty()){
                        buildListFood(success,startColor,endColor)
                    }else{
                        _errorListFood.postValue(ErrorCode.ERROR_SERVICE)
                    }
                }
            }catch (e:java.lang.Exception){
                _errorListFood.postValue(ErrorCode.ERROR_APP)
            }
        }
    }

    private fun buildListFood(items: List<FoodResponse>?,startColor: String, endColor:String) {
        val list: MutableList<ModelCardList> = mutableListOf()
        items?.forEach {
            list.add(
                ModelCardList(
                alimento = it.alimento,
                cantidad_sugerida = it.cantidad_sugerida,
                unidad = it.unidad,
                peso_neto_g = it.peso_neto_g,
                startColor = startColor,
                endColor = endColor,
                peso_bruto_redondeado_g = it.peso_bruto_redondeado_g ,
                energia_kcal = it.energia_kcal,
                proteina_g = it.proteina_g,
                lipidos_g = it.lipidos_g,
                hidratos_de_carbono_g = it.hidratos_de_carbono_g,
                fibra_g = it.fibra_g,
                vitamina_a_ug_re = it.vitamina_a_ug_re,
                acido_ascorbico_mg = it.acido_ascorbico_mg,
                acido_folico_ug = it.acido_folico_ug,
                hierro_no_hem_mg = it.hierro_no_hem_mg,
                potasio_mg = it.potasio_mg,
                indice_glicemico = it.indice_glicemico,
                carga_glicemica = it.carga_glicemica,
                azucar_por_equivalente_g = it.azucar_por_equivalente_g,
                calcio_mg= it.calcio_mg,
                hierro_mg = it.hierro_mg,
                sodio_mg = it.sodio_mg,
                colesterol_mg = it.colesterol_mg,
                selenio_mg = it.selenio_mg,
                selenio_ug = it.selenio_ug,
                fosforo_mg = it.fosforo_mg,
                ag_saturados_g = it.ag_saturados_g,
                ag_monoinsaturados_g = it.ag_monoinsaturados_g,
                ag_poliinsaturados_g = it.ag_poliinsaturados_g,
                categoria =it.categoria
            )
            )
        }

        val listFood: MutableList<ModelCardList> = mutableListOf()
        listFood.addAll(list)
        _listFood.postValue(listFood)
    }

    fun buildTextAlert(modelCardList: ModelCardList): StringBuilder {
        val cadena: StringBuilder = StringBuilder()
        with(modelCardList) {
            cantidad_sugerida.let { cadena.append("Cantidad sugerida: ").append(it).append("\n") }
            unidad.let { cadena.append("Unidad: ").append(it).append("\n") }
            peso_neto_g.let { cadena.append("Peso neto: ").append(it).append(" g").append("\n") }
            peso_bruto_redondeado_g.let { cadena.append("Peso bruto redondeado: ").append(it.toString()).append(" g").append("\n")}
            energia_kcal.let { cadena.append("Energía: ").append(it.toString()).append(" Kcal").append("\n")}
            proteina_g?.let { cadena.append("Proteína: ").append(it.toString()).append(" g").append("\n")}
            lipidos_g?.let { cadena.append("Lípidos: ").append(it.toString()).append(" g").append("\n")}
            hidratos_de_carbono_g?.let { cadena.append("Hidratos de carbono: ").append(it.toString()).append(" g").append("\n")}
            fibra_g?.let { cadena.append("Fibra: ").append(it.toString()).append(" g").append("\n")}
            vitamina_a_ug_re?.let { cadena.append("Vitamina A ug re: ").append(it.toString()).append("\n")}
            acido_ascorbico_mg?.let { cadena.append("Ácido ascórbico: ").append(it.toString()).append(" mg").append("\n")}
            acido_folico_ug?.let { cadena.append("Ácido fólico: ").append(it.toString()).append(" ug").append("\n")}
            hierro_no_hem_mg?.let { cadena.append("Hiero no hem: ").append(it.toString()).append(" mg").append("\n")}
            potasio_mg?.let { cadena.append("Potasio: ").append(it.toString()).append(" mg").append("\n")}
            indice_glicemico?.let { cadena.append("Índice glicémico: ").append(it.toString()).append("\n")}
            carga_glicemica?.let { cadena.append("Carga glicémica: ").append(it.toString()).append("\n")}
            azucar_por_equivalente_g?.let { cadena.append("Azúcar por equivalente: ").append(it.toString()).append(" g").append("\n") }
            calcio_mg?.let { cadena.append("Calcio: ").append(it.toString()).append(" mg").append("\n") }
            hierro_mg?.let { cadena.append("Hierro: ").append(it.toString()).append(" mg").append("\n") }
            sodio_mg?.let { cadena.append("Sodio: ").append(it.toString()).append(" mg").append("\n") }
            colesterol_mg?.let { cadena.append("Colesterol: ").append(it.toString()).append(" mg").append("\n") }
            selenio_mg?.let { cadena.append("Selenio: ").append(it.toString()).append(" mg").append("\n") }
            selenio_ug?.let { cadena.append("Selenio: ").append(it.toString()).append(" ug").append("\n") }
            fosforo_mg?.let { cadena.append("Fósforo: ").append(it.toString()).append(" mg").append("\n") }
            ag_saturados_g?.let { cadena.append("AG Saturados: ").append(it.toString()).append(" g").append("\n") }
            ag_monoinsaturados_g?.let { cadena.append("AG Monoinsaturados: ").append(it.toString()).append(" g").append("\n") }
            ag_poliinsaturados_g?.let { cadena.append("AG Polinsaturados: ").append(it.toString()).append(" g").append("\n") }
        }
        return cadena
    }

}