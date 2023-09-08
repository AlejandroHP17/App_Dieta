package com.liftechnology.planalimenticio.ui.view.table

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.FragmentTableBinding
import com.liftechnology.planalimenticio.model.interfaces.DialogListener
import com.liftechnology.planalimenticio.ui.adapters.SectionClickedListener
import com.liftechnology.planalimenticio.ui.adapters.TableAdapter
import com.liftechnology.planalimenticio.ui.utils.CustomSelectDialog
import com.liftechnology.planalimenticio.ui.utils.TableNumberMeal
import com.liftechnology.planalimenticio.ui.utils.ValidateTextDialogSelect
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel
import com.liftechnology.planalimenticio.ui.viewmodel.VMTable
import kotlinx.coroutines.launch
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
        viewModelTable.startTable(requireContext())
        // Inicializa los observers
        initObservers()
        // Inicializa los listeners
        initListenerAdapter(5)

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

    /** Inicializa los observadores del viewmodel, para saber sus cambios
     * @author pelkidev
     * @date 01/09/2023
     * */
    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModelTable.dataFlow.collect{
                    adapterTable.submitList(it)
                    // Construye el recycler con el adaptador
                    binding.recyclerTable.adapter = adapterTable
                }
            }
        }

        viewModelTable.typeClick.observe(viewLifecycleOwner){ _ ->
            showDialog(getString(R.string.button_number_meals), ValidateTextDialogSelect.MEALS , viewModelTable.numberMeals)
        }
    }

    /** Inicializa los listeners
     * @author pelkidev
     * @date 01/09/2023
     * */
    private fun initListenerAdapter(numberMeal:Int) {

        adapterTable = TableAdapter(SectionClickedListener{card , click->

            when(click) {
                TableNumberMeal.CATEGORY  -> { showDialog(card.category?.first!!,ValidateTextDialogSelect.PORTION,card.category?.second!!)}
                TableNumberMeal.M1 -> {showDialog(card.category?.first!!,ValidateTextDialogSelect.QUANTITY,card.meal1?.second!!)}
                TableNumberMeal.M2-> {showDialog(card.category?.first!!,ValidateTextDialogSelect.QUANTITY,card.meal2?.second!!)}
                TableNumberMeal.M3-> {showDialog(card.category?.first!!,ValidateTextDialogSelect.QUANTITY,card.meal3?.second!!)}
                TableNumberMeal.M4 -> {showDialog(card.category?.first!!,ValidateTextDialogSelect.QUANTITY,card.meal4?.second!!)}
                TableNumberMeal.M5 -> {showDialog(card.category?.first!!,ValidateTextDialogSelect.QUANTITY,card.meal5?.second!!)}
                TableNumberMeal.M6 -> {showDialog(card.category?.first!!,ValidateTextDialogSelect.QUANTITY,card.meal6?.second!!)}
                TableNumberMeal.M7 -> {showDialog(card.category?.first!!,ValidateTextDialogSelect.QUANTITY,card.meal7?.second!!)}

                else -> {
                    // Clic en un elemento no identificado
                }
            }
        },numberMeal
        )

    }

    private fun showDialog(title: String, body:String, number:Int){
        val dialogFragment = CustomSelectDialog.newInstance(title, body, number)
        dialogFragment.dialogListener = this
        fragmentManager?.let {
            dialogFragment.show(it, "customDialog")
        }
    }

    override fun onDataReceivedMeals(data: Int) {
        binding.btnNumberMeals.text = getString(R.string.button_number_meals) + data
        viewModelTable.numberMeals = data
        adapterTable.updateMealsCount(data)
    }

    override fun onDataReceivedQuantity(data: Int) {

    }

    override fun onDataReceivedPortion(data: Int) {

    }
}