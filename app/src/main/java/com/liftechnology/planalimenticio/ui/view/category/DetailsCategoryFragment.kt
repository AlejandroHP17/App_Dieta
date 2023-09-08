package com.liftechnology.planalimenticio.ui.view.category

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.data.network.models.response.local.ModelCardList
import com.liftechnology.planalimenticio.databinding.FragmentDetailsCategoryBinding
import com.liftechnology.planalimenticio.ui.adapters.FoodClickedListener
import com.liftechnology.planalimenticio.ui.adapters.GeneralAdapter
import com.liftechnology.planalimenticio.ui.utils.CustomDetailDialog
import com.liftechnology.planalimenticio.ui.viewextensions.toastFragment
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel
import com.liftechnology.planalimenticio.ui.viewmodel.VMCategory
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @author pelkidev
 * @date 21/08/2023
 * */
class DetailsCategoryFragment : Fragment() {

    /* Variables iniciales */
    private lateinit var binding: FragmentDetailsCategoryBinding
    private val vmCategory: VMCategory by sharedViewModel()
    private val viewModelMain: AllViewModel by sharedViewModel()


    /* Variable para el recycler */
    private lateinit var adapterCategory: GeneralAdapter

    // Variable para recibir los argumentos
    private var dataNavigate : CategoryResponse? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /* Inicializa la vista con binding y viewmodel */
        binding = FragmentDetailsCategoryBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vmList = vmCategory
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Obtiene los argumentos de navegación
        getArgument()
        // Inicializa las vistas
        initView()
        // Inicializa los observers
        initObservers()
        // Inicializa los listeners
        initListeners()
    }

    /** Obtiene los argumentos de navegación
     * @author pelkidev
     * @date 21/08/2023
     * */
    private fun getArgument(){
        dataNavigate = DetailsCategoryFragmentArgs.fromBundle(requireArguments()).arg
        // Sale al servicio una vez obteniendo el argumento
        vmCategory.getListFood(dataNavigate!!)
        viewModelMain.colorGeneral = dataNavigate?.startColor
    }

    /** Inicializa la vista con parametros iniciales
     * @author pelkidev
     * @date 21/08/2023
     * */
    private fun initView(){
        /* Toolbar: Se configura de manera inicial */
        binding.toolbarCategory.btnReturn.visibility = View.VISIBLE
        binding.toolbarCategory.btnSearchBar.visibility = View.GONE
        binding.allContent.visibility = View.GONE
        binding.toolbarCategory.tvNameCategory.text = dataNavigate?.category
        binding.toolbarCategory.toolbar.setBackgroundColor(Color.parseColor(dataNavigate?.startColor))
    }


    /** Inicializa los observadores del viewmodel, para saber sus cambios
     * @author pelkidev
     * @date 21/08/2023
     * */
    private fun initObservers() {
        // Observador del servicio de la lista de alimentos
        vmCategory.listFood.observe(viewLifecycleOwner, handlerListFood())
        vmCategory.errorListFood.observe(viewLifecycleOwner, handlerErrorListFood())
    }

    /** Inicializa los listeners
     * @author pelkidev
     * @date 21/08/2023
     * */
    private fun initListeners() {
        /* Maneja el click del card del adapter */
        adapterCategory = GeneralAdapter(FoodClickedListener {
            showDialog( it.food, vmCategory.buildTextAlert(it))
        })

        binding.toolbarCategory.btnReturn.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    /** Respuesta de la lista de alimentos al cambiar su valor
     * @author pelkidev
     * @date 21/08/2023
     * @param [List<FoodResponse>] Contiene el listado de alimentos
     * */
    private fun handlerListFood(): (List<ModelCardList>) -> Unit = { list ->
        // Add the items to the adapter
        adapterCategory.submitList(list)
        // Build the adapter
        binding.recyclerCardsList.adapter = adapterCategory
        binding.allContent.visibility = View.VISIBLE
    }

    /** Error en el servicio
     * @author pelkidev
     * @date 21/08/2023
     * */
    private fun handlerErrorListFood(): (String) -> Unit = { text ->
        // Muestra el error
        toastFragment(text)
        // Regresa a la vista anterior
        findNavController().popBackStack()
    }

    private fun showDialog(title: String, body:StringBuilder){
        val dialogFragment = CustomDetailDialog.newInstance(title, body.toString())
        fragmentManager?.let {
            dialogFragment.show(it, "customDialog")
        }
    }
}