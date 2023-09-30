package com.liftechnology.planalimenticio.ui.view.table

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.FragmentTableBinding
import com.liftechnology.planalimenticio.framework.BaseFragment
import com.liftechnology.planalimenticio.model.dataclass.TypeMeals
import com.liftechnology.planalimenticio.model.dataclass.TypeTable
import com.liftechnology.planalimenticio.model.interfaces.DialogListener
import com.liftechnology.planalimenticio.ui.adapters.SectionClickedListener
import com.liftechnology.planalimenticio.ui.adapters.TableAdapter
import com.liftechnology.planalimenticio.ui.utils.TableNumberMeal
import com.liftechnology.planalimenticio.ui.utils.TypeClick.CLEAN
import com.liftechnology.planalimenticio.ui.utils.TypeClick.NUMBER_MEALS
import com.liftechnology.planalimenticio.ui.utils.ValidateTextDialogSelect
import com.liftechnology.planalimenticio.ui.view.dialog.CustomSaveDialog
import com.liftechnology.planalimenticio.ui.view.dialog.CustomSelectDialog
import com.liftechnology.planalimenticio.ui.viewextensions.toastSuccess
import com.liftechnology.planalimenticio.ui.viewmodel.ShareViewModel
import com.liftechnology.planalimenticio.ui.viewmodel.VMTable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @author pelkidev
 * @date 01/09/2023
 * */
class TableFragment : BaseFragment<FragmentTableBinding>(), DialogListener {

    // Vista al fragment base
    override fun getViewBinding() = FragmentTableBinding.inflate(layoutInflater)
    /* Variable del viewModel */
    private val viewModelTable: VMTable by sharedViewModel()
    private val shareModelMain: ShareViewModel by sharedViewModel()

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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vmTable = viewModelTable
        return binding.root
    }

    /** Inicializa la vista con parametros iniciales
     * @author pelkidev
     * @date 20/09/2023
     * */
    override fun setUpViews() {
        super.setUpViews()
        /* Toolbar: Se configura de manera inicial */
        binding.toolbarCategory.tvNameCategory.text = getString(R.string.toolbar_txt_table)
        binding.btnNumberMeals.text = getString(R.string.button_number_meals)
        binding.toolbarCategory.btnSave.visibility = View.VISIBLE
        binding.toolbarCategory.toolbar.setBackgroundColor(Color.parseColor(shareModelMain.colorGeneral))
    }

    /** Inicializa informacion primaria
     * @author pelkidev
     * @date 20/09/2023
     * */
    override fun initData() {
        titleNumberMeals = getString(R.string.button_number_meals)
        val typeTable = TypeTable(listOf(), Pair(titleNumberMeals, 3))
        viewModelTable.startTable(requireContext(), typeTable)

        initListenerAdapter()
        listTable = listOf()
        adapterTable.submitList(listTable)
        // Construye el recycler con el adaptador
        binding.recyclerTable.adapter = adapterTable
    }

    override fun listenersView() {
        super.listenersView()
        binding.toolbarCategory.btnSave.setOnClickListener { showDialogSave() }

    }


    /** Inicializa los observadores del viewmodel, para saber sus cambios
     * @author pelkidev
     * @date 20/09/2023
     * */
    override fun observeData() {
        super.observeData()
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
                when(value){
                    NUMBER_MEALS -> {showDialog(
                        getString(R.string.button_number_meals),
                        ValidateTextDialogSelect.MEALS,
                        "")}
                    CLEAN -> {
                        titleNumberMeals = getString(R.string.button_number_meals)
                        viewModelTable.cleanTable(requireContext(),Pair(titleNumberMeals, 3))
                    }
                }
            } else {
                isInitial = true
            }
        }
    }

    private fun showDialogSave() {
        val dialogFragment = CustomSaveDialog()
        dialogFragment.dialogListener = this
        childFragmentManager.let {
            dialogFragment.show(it, "customDialog")
        }
    }


    /** Inicializa los listeners
     * @author pelkidev
     * @date 01/09/2023
     * */
    private fun initListenerAdapter() {
        val numberMeal = 3
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
        childFragmentManager.let {
            dialogFragment.show(it, "customDialog")
        }
    }

    override fun onDataUpdateTable() {
        toastSuccess("La informacion se ha actualizado correctamente", requireActivity())
        viewModelTable.getTable(requireContext())
    }

    override fun onSaveTable() {
        toastSuccess("La informacion se ha almacenado correctamente", requireActivity())
    }


}