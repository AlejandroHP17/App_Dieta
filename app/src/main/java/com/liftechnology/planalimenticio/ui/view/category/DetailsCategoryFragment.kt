package com.liftechnology.planalimenticio.ui.view.category

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.data.local.ModelCardList
import com.liftechnology.planalimenticio.data.network.models.response.FoodResponse
import com.liftechnology.planalimenticio.databinding.DialogCustomDetailBinding
import com.liftechnology.planalimenticio.databinding.FragmentDetailsCategoryBinding
import com.liftechnology.planalimenticio.ui.adapters.FoodClickedListener
import com.liftechnology.planalimenticio.ui.adapters.GeneralAdapter
import com.liftechnology.planalimenticio.ui.viewmodel.VMCategory


class DetailsCategoryFragment : Fragment() {

    private lateinit var binding: FragmentDetailsCategoryBinding
    private lateinit var bindingDialog: DialogCustomDetailBinding
    private val vmCategory: VMCategory by viewModels()

    private lateinit var dialog : AlertDialog

    /** Variable to recycler */
    private lateinit var adapterCategory: GeneralAdapter
    private var listCategory: MutableList<ModelCardList>? = mutableListOf()

    private var dataNavigate : Array<String> ?= null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsCategoryBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.vmList = vmCategory

        val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate)
        binding.imageRotate.startAnimation(rotateAnimation)

        dataNavigate = DetailsCategoryFragmentArgs.fromBundle(requireArguments()).data
        binding.toolbarCategory.tvNameCategory.text = dataNavigate!![3]

        bindingDialog = DialogCustomDetailBinding.inflate(inflater)
        dialog = AlertDialog.Builder(requireContext())
            .setView(bindingDialog.root)
            .create()
        dialog.setCancelable(false)


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
        initListeners()
        vmCategory.getItemVegetable(dataNavigate!![2])
    }

    private fun initView(){
        binding.toolbarCategory.btnReturn.visibility = View.GONE
        binding.toolbarCategory.btnSearchBar.visibility = View.GONE
    }



    private fun initListeners() {
        adapterCategory = GeneralAdapter(FoodClickedListener { it ->
            bindingDialog.txtTitle.text = it.alimento

            var cadena: StringBuilder = StringBuilder()
            with(it) {
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
                azucar_equivalente_g?.let { cadena.append("Ázucar equivalente: ").append(it.toString()).append(" g").append("\n") }
                azucares_por_equivalente_g?.let { cadena.append("Ázucar equivalente: ").append(it.toString()).append(" g").append("\n") }
                ag_saturados_g?.let { cadena.append("AG Saturados: ").append(it.toString()).append(" g").append("\n") }
                ag_monoinsaturados_g?.let { cadena.append("AG Monoinsaturados: ").append(it.toString()).append(" g").append("\n") }
                ag_poliinsaturados_g?.let { cadena.append("AG Polinsaturados: ").append(it.toString()).append(" g").append("\n") }
            }

            bindingDialog.txtDescription.text = cadena
            dialog.show()
        })
        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
        }

    }

    private fun initObservers() {
        vmCategory.getVegetable.observe(viewLifecycleOwner, handlerVegetable())
    }


    private fun handlerVegetable():(List<FoodResponse>) -> Unit = { items ->

        val list: MutableList<ModelCardList> = mutableListOf()
        items.forEach { it ->
            list.add(ModelCardList(
                alimento = it.alimento,
                cantidad_sugerida = it.cantidad_sugerida,
                unidad = it.unidad,
                peso_neto_g = it.peso_neto_g,
                startColor = dataNavigate!![0],
                endColor = dataNavigate!![1],
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
                azucar_equivalente_g = it.azucar_equivalente_g,
                azucares_por_equivalente_g = it.azucares_por_equivalente_g,
                ag_saturados_g = it.ag_saturados_g,
                ag_monoinsaturados_g = it.ag_monoinsaturados_g,
                ag_poliinsaturados_g = it.ag_poliinsaturados_g,
                categoria =it.categoria
            ))
        }

        // Verifica si la lista es null y, en caso afirmativo, crea una colección vacía
        listCategory?.addAll(list)
        // Add the items to the adapter
        adapterCategory.submitList(listCategory)
        // Build the adapter
        binding.recyclerCardsList.adapter = adapterCategory

        binding.imageRotate.visibility= View.GONE
        binding.imageRotate.clearAnimation()
        binding.allContent.visibility = View.VISIBLE
    }

}