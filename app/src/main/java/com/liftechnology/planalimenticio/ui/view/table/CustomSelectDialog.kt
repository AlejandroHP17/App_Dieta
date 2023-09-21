package com.liftechnology.planalimenticio.ui.view.table

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.DialogCustomSelectBinding
import com.liftechnology.planalimenticio.model.dataclass.TypeMeals
import com.liftechnology.planalimenticio.model.dataclass.TypeTable
import com.liftechnology.planalimenticio.model.interfaces.DialogListener
import com.liftechnology.planalimenticio.ui.utils.TableNumberMeal
import com.liftechnology.planalimenticio.ui.utils.ValidateTextDialogSelect
import com.liftechnology.planalimenticio.ui.viewmodel.ShareViewModel
import com.liftechnology.planalimenticio.ui.viewmodel.VMTable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CustomSelectDialog : DialogFragment() {

    private val shareModelMain: ShareViewModel by sharedViewModel()
    var dialogListener: DialogListener? = null
    private val viewModelTable: VMTable by sharedViewModel()

    private var argTitle: String? = null
    private var argDescription: String? = null
    private var argNumber: Int? = null
    private var click : String? = null
    private var argStructure: TypeTable? = null

    private var allTable : TypeTable? = null

    private lateinit var bindingDialog :DialogCustomSelectBinding

    companion object {
        fun newInstance(title: String, body: String, click: String, structure: TypeTable): CustomSelectDialog {
            val fragment = CustomSelectDialog()
            val args = Bundle()
            args.putString("title", title)
            args.putString("body", body)
            args.putString("click", click)
            args.putSerializable("structure", structure)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val dialog = builder.create()

        // Recuperar los argumentos
        getArgumentsNav()

        // Configura el estilo de animación personalizado
        dialog.window?.attributes?.windowAnimations = R.style.CustomDialogAnimation

        // Infla y configura tu contenido personalizado en el cuadro de diálogo
        initView()

        // Configura la vista personalizada en el cuadro de diálogo
        dialog.setView(bindingDialog.root)

        initConfigurations()

        initListeners()

        return dialog
    }



    private fun getArgumentsNav(){
        argTitle = arguments?.getString("title")
        argDescription = arguments?.getString("body")
        argNumber = arguments?.getInt("number")
        click = arguments?.getString("click")
        argStructure = arguments?.getSerializable("structure") as TypeTable?

        /* Condiciones iniciales */
        when (argDescription) {
            ValidateTextDialogSelect.MEALS -> {
                argNumber = argStructure?.meals?.second!!
            }
            ValidateTextDialogSelect.PORTION -> {
                argNumber = argStructure?.list?.firstOrNull { it.category?.first == argTitle }?.category?.second ?: 0
            }
            ValidateTextDialogSelect.QUANTITY -> {
                argNumber = argStructure?.list?.firstOrNull { it.category?.first == argTitle }?.let { meal ->
                    when (click) {
                        TableNumberMeal.M1 -> meal.meal1?.second
                        TableNumberMeal.M2 -> meal.meal2?.second
                        TableNumberMeal.M3 -> meal.meal3?.second
                        TableNumberMeal.M4 -> meal.meal4?.second
                        TableNumberMeal.M5 -> meal.meal5?.second
                        TableNumberMeal.M6 -> meal.meal6?.second
                        TableNumberMeal.M7 -> meal.meal7?.second
                        else -> 0 // Valor predeterminado en caso de que click no coincida con ninguna opción
                    }
                } ?: 0
            }
        }


    }


    private fun initView(){
        bindingDialog = DialogCustomSelectBinding.inflate(layoutInflater)

        when (argDescription){
            ValidateTextDialogSelect.PORTION -> bindingDialog.txtDescription.text = getString(R.string.dialog_change_portion)
            ValidateTextDialogSelect.QUANTITY -> bindingDialog.txtDescription.text = getString(R.string.dialog_change_quantity)
            ValidateTextDialogSelect.MEALS -> bindingDialog.txtDescription.text = getString(R.string.dialog_change_meals)
        }
        bindingDialog.txtTitle.text = argTitle
        bindingDialog.textQuantity.text = argNumber.toString()
        bindingDialog.btnClose.setBackgroundColor(Color.parseColor(shareModelMain.colorGeneral))
    }

    private fun initConfigurations(){
        dialog?.setCanceledOnTouchOutside(false)
    }

    private fun initListeners(){
        bindingDialog.apply {
            btnClose.setOnClickListener { dismiss() }

            btnAccept.setOnClickListener {
                updateData()
                dialogListener?.onDataUpdateTable()
                dismiss()
            }

            btnPlus.setOnClickListener { makeSum() }

            btnMinus.setOnClickListener { makeSubtract() }
        }
    }

    private fun makeSubtract() {
        if(argDescription == ValidateTextDialogSelect.MEALS){
            if (argNumber!=3){
                argNumber = argNumber!! - 1
                bindingDialog.textQuantity.text = argNumber.toString()
            }
        }else{
            if (argNumber!=0){
                argNumber = argNumber!! - 1
                bindingDialog.textQuantity.text = argNumber.toString()
            }
        }
    }

    private fun makeSum() {
        if (argDescription == ValidateTextDialogSelect.MEALS){
            if (argNumber!=7) {
                argNumber = argNumber!! + 1
                bindingDialog.textQuantity.text = argNumber.toString()
            }
        }else{
            if (argNumber!=10) {
                argNumber = argNumber!! + 1
                bindingDialog.textQuantity.text = argNumber.toString()
            }
        }
    }


    private fun updateData() {
        var updateList = argStructure?.list
        var updateFirstTable = argStructure?.meals
        val updateListFinish = updateList?.toMutableList()

        when (argDescription) {
            ValidateTextDialogSelect.MEALS -> {
                updateFirstTable = Pair(updateFirstTable?.first!!, argNumber!!)
                val list = context?.resources?.getStringArray(R.array.table_category)
                val listAdapter = mutableListOf<TypeMeals>()

                /* Construye con datos base */
                list?.forEach {
                    updateListFinish?.add(
                        TypeMeals(
                            category = Pair(it, 0),
                            meal1 = Pair("C1", 0),
                            meal2 = Pair("C2", 0),
                            meal3 = Pair("C3", 0),
                            meal4 = Pair("C4", 0),
                            meal5 = Pair("C5", 0),
                            meal6 = Pair("C6", 0),
                            meal7 = Pair("C7", 0)
                        )
                    )
                }
            }
            ValidateTextDialogSelect.PORTION -> {
                updateListFinish?.let { list ->
                    updateList = list.map {
                        if (it.category?.first == argTitle) {
                            it.copy(category = Pair(it.category?.first!!, argNumber!!))
                        } else {
                            it
                        }
                    }.toList()
                }
            }
            ValidateTextDialogSelect.QUANTITY -> {
                updateListFinish?.let { list ->
                    updateList = list.map {
                        if (it.category?.first == argTitle) {
                            when (click) {
                                TableNumberMeal.M1 -> it.copy(meal1 = Pair(it.meal1?.first!!, argNumber!!))
                                TableNumberMeal.M2 -> it.copy(meal2 = Pair(it.meal2?.first!!, argNumber!!))
                                TableNumberMeal.M3 -> it.copy(meal3 = Pair(it.meal3?.first!!, argNumber!!))
                                TableNumberMeal.M4 -> it.copy(meal4 = Pair(it.meal4?.first!!, argNumber!!))
                                TableNumberMeal.M5 -> it.copy(meal5 = Pair(it.meal5?.first!!, argNumber!!))
                                TableNumberMeal.M6 -> it.copy(meal6 = Pair(it.meal6?.first!!, argNumber!!))
                                TableNumberMeal.M7 -> it.copy(meal7 = Pair(it.meal7?.first!!, argNumber!!))
                                else->{it.copy(meal7 = Pair(it.meal7?.first!!, argNumber!!))}
                            }
                        } else {
                            it
                        }
                    }.toList()
                }
            }
        }
        allTable = TypeTable(updateList!!,updateFirstTable)
        viewModelTable.updateTable(requireContext(), allTable!!)
    }
}