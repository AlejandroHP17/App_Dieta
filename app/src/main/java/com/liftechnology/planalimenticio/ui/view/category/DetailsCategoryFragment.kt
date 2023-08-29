package com.liftechnology.planalimenticio.ui.view.category

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.liftechnology.planalimenticio.data.local.ModelCardList
import com.liftechnology.planalimenticio.databinding.DialogCustomDetailBinding
import com.liftechnology.planalimenticio.databinding.FragmentDetailsCategoryBinding
import com.liftechnology.planalimenticio.ui.adapters.FoodClickedListener
import com.liftechnology.planalimenticio.ui.adapters.GeneralAdapter
import com.liftechnology.planalimenticio.ui.viewextensions.initAnim
import com.liftechnology.planalimenticio.ui.viewextensions.toastFragment
import com.liftechnology.planalimenticio.ui.viewmodel.VMCategory

/**
 * @author pelkidev
 * @date 21/08/2023
 * */
class DetailsCategoryFragment : Fragment() {

    /* Variables iniciales */
    private lateinit var binding: FragmentDetailsCategoryBinding
    private lateinit var bindingDialog: DialogCustomDetailBinding
    private val vmCategory: VMCategory by viewModels()

    // Variable para el AlertDialog
    private lateinit var dialog : AlertDialog

    /* Variable para el recycler */
    private lateinit var adapterCategory: GeneralAdapter

    // Variable para recibir los argumentos
    private var dataNavigate : Array<String> ?= null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /* Inicializa la vista con binding y viewmodel */
        binding = FragmentDetailsCategoryBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vmList = vmCategory
        // Inicializa el AlertDialog
        bindingDialog = DialogCustomDetailBinding.inflate(inflater)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Obtiene los argumentos de navegación
        getArgument()
        // Inicializa las vistas
        initView()
        // Inicializa el Alert Dialog
        initAlertDialog()
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
        dataNavigate = DetailsCategoryFragmentArgs.fromBundle(requireArguments()).data
        // Sale al servicio una vez obteniendo el argumento
        vmCategory.getListFood(dataNavigate!![2],dataNavigate!![0], dataNavigate!![1])
    }

    /** Inicializa la vista con parametros iniciales
     * @author pelkidev
     * @date 21/08/2023
     * */
    private fun initView(){
        /* Toolbar: Se configura de manera inicial */
        binding.toolbarCategory.btnReturn.visibility = View.GONE
        binding.toolbarCategory.btnSearchBar.visibility = View.GONE
        binding.toolbarCategory.tvNameCategory.text = dataNavigate!![3]
        /* Inicia el loader */
        context?.let { binding.imageRotate.initAnim(it) }
    }

    /** Inicializa el Alert Dialog
     * @author pelkidev
     * @date 21/08/2023
     * */
    private fun initAlertDialog() {
        dialog = AlertDialog.Builder(requireContext())
            .setView(bindingDialog.root)
            .create()
        dialog.setCancelable(false)
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
            // Pinta en el titulo del alert el nombre del alimento
            bindingDialog.txtTitle.text = it.alimento
            // Pinta en el cuerpo del alert toda la informacion que contiene dicho aliemento
            bindingDialog.txtDescription.text = vmCategory.buildTextAlert(it)
            dialog.show()
        })

        /* Maneja el click cerrar del alert */
        bindingDialog.btnClose.setOnClickListener {
            dialog.dismiss()
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
        /* Cambios en la vista del loader */
        binding.imageRotate.visibility= View.GONE
        binding.imageRotate.clearAnimation()
        binding.allContent.visibility = View.VISIBLE
    }

    /** Error en el servicio
     * @author pelkidev
     * @date 21/08/2023
     * @param [String] Contiene el tipo de error
     * */
    private fun handlerErrorListFood(): (String) -> Unit = { text ->
        // Muestra el error
        toastFragment(text)
        // Regresa a la vista anterior
        findNavController().popBackStack()
    }

}