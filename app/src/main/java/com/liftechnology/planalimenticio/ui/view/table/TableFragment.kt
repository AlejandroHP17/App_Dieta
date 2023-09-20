package com.liftechnology.planalimenticio.ui.view.table

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.FragmentTableBinding
import com.liftechnology.planalimenticio.model.dataclass.TypeMeals
import com.liftechnology.planalimenticio.model.dataclass.TypeTable
import com.liftechnology.planalimenticio.model.interfaces.DialogListener
import com.liftechnology.planalimenticio.ui.adapters.SectionClickedListener
import com.liftechnology.planalimenticio.ui.adapters.TableAdapter
import com.liftechnology.planalimenticio.ui.utils.CustomSelectDialog
import com.liftechnology.planalimenticio.ui.utils.TableNumberMeal
import com.liftechnology.planalimenticio.ui.utils.ValidateTextDialogSelect
import com.liftechnology.planalimenticio.ui.viewextensions.toastFragment
import com.liftechnology.planalimenticio.ui.viewextensions.toastSuccess
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel
import com.liftechnology.planalimenticio.ui.viewmodel.VMTable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @author pelkidev
 * @date 01/09/2023
 * */
class TableFragment : Fragment(), DialogListener {
    /* Variables iniciales */
    private lateinit var binding: FragmentTableBinding
    private val viewModelTable: VMTable by sharedViewModel()
    private val viewModelMain: AllViewModel by sharedViewModel()

    /* Variable para el recycler */
    private lateinit var adapterTable: TableAdapter
    private lateinit var listTable: List<TypeMeals>

    /* Variables auxiliares */
    private var titleNumberMeals = ""
    private var isInitial = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /* Inicializa la vista con binding (databinding )y viewmodel */
        binding = FragmentTableBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vmTable = viewModelTable
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicializa los elementos de la vista
        initView()
        // Inicializa los datos de la tabla
        initData()
        // Inicializa los observers
        initObservers()
        // Inicializa los listeners
    }


    /** Inicializa la vista con parametros iniciales
     * @author pelkidev
     * @date 01/09/2023
     * */
    private fun initView() {
        /* Toolbar: Se configura de manera inicial */
        binding.toolbarCategory.btnReturn.visibility = View.GONE
        binding.toolbarCategory.btnSearchBar.visibility = View.GONE
        binding.toolbarCategory.tvNameCategory.text = getString(R.string.toolbar_txt_table)
        binding.btnNumberMeals.text = getString(R.string.button_number_meals)
        binding.toolbarCategory.toolbar.setBackgroundColor(Color.parseColor(viewModelMain.colorGeneral))
    }

    private fun initData() {
        titleNumberMeals = getString(R.string.button_number_meals)
        val typeTable = TypeTable(listOf(), Pair(titleNumberMeals, 3))
        viewModelTable.startTable(requireContext(), typeTable)

        initListenerAdapter(3)
        listTable = listOf<TypeMeals>()
        adapterTable.submitList(listTable)
        // Construye el recycler con el adaptador
        binding.recyclerTable.adapter = adapterTable
    }

    /** Inicializa los observadores del viewmodel, para saber sus cambios
     * @author pelkidev
     * @date 01/09/2023
     * */
    private fun initObservers() {
        viewModelTable.dataFlow.observe(viewLifecycleOwner) { data ->
            if (data.list.isNotEmpty()) {
                viewModelTable.numberMeals = data.meals?.second!!
                binding.recyclerTable.adapter = adapterTable
                binding.btnNumberMeals.text = getString(R.string.button_number_meals, data.meals.second)
                adapterTable.submitList(data.list)
                adapterTable.updateMealsCount(data.meals.second)

            }
        }


        viewModelTable.typeClick.observe(viewLifecycleOwner) { value ->
            if (isInitial) {
                if(value == "number"){
                    showDialog(
                        getString(R.string.button_number_meals),
                        ValidateTextDialogSelect.MEALS,
                        ""
                    )
                }else{
                    titleNumberMeals = getString(R.string.button_number_meals)
                    viewModelTable.cleanTable(requireContext(),Pair(titleNumberMeals, 3))
                }

            } else {
                isInitial = true
            }

        }


    }

    /** Inicializa los listeners
     * @author pelkidev
     * @date 01/09/2023
     * */
    private fun initListenerAdapter(numberMeal: Int) {
        adapterTable = TableAdapter(
            SectionClickedListener { card, click ->
                viewModelTable.dataFlow
                when (click) {
                    TableNumberMeal.CATEGORY -> {
                        showDialog(
                            title = card.category?.first!!,
                            body = ValidateTextDialogSelect.PORTION,
                            click = click
                        )
                    }
                    else -> {
                        showDialog(
                            title = card.category?.first!!,
                            body = ValidateTextDialogSelect.QUANTITY,
                            click = click
                        )
                    }
                }
            }, numberMeal
        )

    }

    private fun showDialog(title: String, body: String, click: String) {
        val structure =  viewModelTable.dataFlow.value!!
        val dialogFragment = CustomSelectDialog.newInstance(title, body, click, structure)
        dialogFragment.dialogListener = this
        fragmentManager?.let {
            dialogFragment.show(it, "customDialog")
        }
    }

    override fun onDataReceivedTable() {
        toastSuccess("La informacion se ha actualizado correctamente", requireActivity())
        viewModelTable.getTable(requireContext())
    }

}