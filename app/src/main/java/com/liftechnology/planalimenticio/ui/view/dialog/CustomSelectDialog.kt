package com.liftechnology.planalimenticio.ui.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.DialogFragment
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.DialogCustomSelectBinding
import com.liftechnology.planalimenticio.model.dataclass.TypeMeals
import com.liftechnology.planalimenticio.model.dataclass.TypeTable
import com.liftechnology.planalimenticio.model.interfaces.DialogListener
import com.liftechnology.planalimenticio.ui.utils.TableNumberMeal
import com.liftechnology.planalimenticio.ui.utils.ValidateTextDialogSelect
import com.liftechnology.planalimenticio.ui.viewextensions.toastFailed
import com.liftechnology.planalimenticio.ui.viewmodel.ShareViewModel
import com.liftechnology.planalimenticio.ui.viewmodel.VMTable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CustomSelectDialog : DialogFragment() {

    /* Variables del viewModel*/
    private val shareModelMain: ShareViewModel by sharedViewModel()
    private val viewModelTable: VMTable by sharedViewModel()

    // Variable del listener
    var dialogListener: DialogListener? = null


    /* Variables de argumentos*/
    private var argTitle: String? = null
    private var argDescription: String? = null
    private var argNumber: Int? = null
    private var click : String? = null
    private var argStructure: TypeTable? = null

    /* Variables para actualizar tabla*/
    private var updateList = argStructure?.list
    private var updateFirstTable = argStructure?.meals

   /* Variables de conteo */
    private var conteo = 0
    private var row = 0

    /* Variables auxiliares */
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

        // Setea configuraciones iniciales
        initConfigurations()

        // Declara los listeners
        initListeners()

        return dialog
    }


    /** Obtiene los argumentos
     * @author pelkidev
     * @date 25/09/2023
     * */
    private fun getArgumentsNav(){
        argTitle = arguments?.getString("title")
        argDescription = arguments?.getString("body")
        argNumber = arguments?.getInt("number")
        click = arguments?.getString("click")
        argStructure = arguments?.getSerializable("structure") as TypeTable?

        /* Condiciones iniciales para asignar valor a la variable arg number */
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
                        else -> 0
                    }
                } ?: 0
            }
        }
    }


    /** Inicializa la vista
     * @author pelkidev
     * @date 25/09/2023
     * */
    private fun initView(){
        bindingDialog = DialogCustomSelectBinding.inflate(layoutInflater)
        /* Asigna el texto dependiendo de quien inició el dialog */
        when (argDescription){
            ValidateTextDialogSelect.PORTION -> bindingDialog.txtDescription.text = getString(R.string.dialog_change_portion)
            ValidateTextDialogSelect.QUANTITY -> bindingDialog.txtDescription.text = getString(R.string.dialog_change_quantity)
            ValidateTextDialogSelect.MEALS -> bindingDialog.txtDescription.text = getString(R.string.dialog_change_meals)
        }
        bindingDialog.txtTitle.text = argTitle
        bindingDialog.textQuantity.text = argNumber.toString()
        bindingDialog.btnClose.setBackgroundColor(Color.parseColor(shareModelMain.colorGeneral))
        bindingDialog.btnAccept.setBackgroundColor(Color.parseColor(shareModelMain.colorGeneral))
    }


    /** Inicializa la configuración inicial
     * @author pelkidev
     * @date 25/09/2023
     * */
    private fun initConfigurations(){
        // Cancelar el click fuera del dialogo
        dialog?.setCanceledOnTouchOutside(false)

        /* Suma la fila de valores por la categoria específica */
        argStructure?.list?.forEach{
            if (it.category?.first.toString() == argTitle){
                row = it.meal1?.second!! + it.meal2?.second!! + it.meal3?.second!! + it.meal4?.second!! + it.meal5?.second!! + it.meal6?.second!! + it.meal7?.second!!
            }
        }
    }


    /** Inicializa los listeners
     * @author pelkidev
     * @date 25/09/2023
     * */
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


    /** Hace la suma
     * @author pelkidev
     * @date 25/09/2023
     * */
    private fun makeSum() {
        /* Valida que tipo de argumento llega para realizar la validación */
        when(argDescription){
            ValidateTextDialogSelect.MEALS -> {
                if (argNumber!=7) {
                    argNumber = argNumber!! + 1
                    bindingDialog.textQuantity.text = argNumber.toString()
                }else{
                    toastFailed(getString(R.string.dialog_error_meals), requireActivity())
                }
            }
            ValidateTextDialogSelect.PORTION -> {
                if (argNumber!=10) {
                    argNumber = argNumber!! + 1
                    bindingDialog.textQuantity.text = argNumber.toString()
                }else{
                    toastFailed(getString(R.string.dialog_error_portion), requireActivity())
                }
            }
            ValidateTextDialogSelect.QUANTITY ->{
                argStructure?.list?.forEach{
                    if (it.category?.first.toString() == argTitle){
                        conteo = row + argNumber!!
                        if (conteo< it.category?.second!!) {
                            argNumber = argNumber!! + 1
                            bindingDialog.textQuantity.text = argNumber.toString()
                        }else{
                            toastFailed(getString(R.string.dialog_error_quantity), requireActivity())
                        }
                    }
                }
            }
        }

    }


    /** Hace la resta
     * @author pelkidev
     * @date 25/09/2023
     * */
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


    /** Actualiza la información en la base de datos
     * @author pelkidev
     * @date 25/09/2023
     * */
    private fun updateData() {
        updateFirstTable = argStructure?.meals
        updateList = argStructure?.list
        val updateListFinish = updateList?.toMutableList()
        when (argDescription) {
            ValidateTextDialogSelect.MEALS -> { updateMeals(updateListFinish) }
            ValidateTextDialogSelect.PORTION -> { updatePortion(updateListFinish) }
            ValidateTextDialogSelect.QUANTITY -> { updateQuantity(updateListFinish) }
        }
        allTable = TypeTable(updateList!!,updateFirstTable)
        viewModelTable.updateTable(requireContext(), allTable!!)
        Handler(Looper.getMainLooper()).postDelayed({},200)
    }

    private fun updateMeals(updateListFinish: MutableList<TypeMeals>?) {
        updateFirstTable = Pair(updateFirstTable?.first!!, argNumber!!)

        /* Construye con datos base */
        updateListFinish?.let { list ->
            updateList = list.map {
                it.copy(category = Pair(it.category?.first!!, 0),
                    meal1 = Pair(it.meal1?.first!!, 0),
                    meal2 = Pair(it.meal2?.first!!, 0),
                    meal3 = Pair(it.meal3?.first!!, 0),
                    meal4 = Pair(it.meal4?.first!!, 0),
                    meal5 = Pair(it.meal5?.first!!, 0),
                    meal6 = Pair(it.meal6?.first!!, 0),
                    meal7 = Pair(it.meal7?.first!!, 0),
                )
            }.toList()
        }
    }

    private fun updatePortion(updateListFinish: MutableList<TypeMeals>?) {
        updateListFinish?.let { list ->
            updateList = list.map {
                it.copy(category = Pair(it.category?.first!!, argNumber!!),
                    meal1 = Pair(it.meal1?.first!!, 0),
                    meal2 = Pair(it.meal2?.first!!, 0),
                    meal3 = Pair(it.meal3?.first!!, 0),
                    meal4 = Pair(it.meal4?.first!!, 0),
                    meal5 = Pair(it.meal5?.first!!, 0),
                    meal6 = Pair(it.meal6?.first!!, 0),
                    meal7 = Pair(it.meal7?.first!!, 0),
                )
            }.toList()
        }
    }

    private fun updateQuantity(updateListFinish: MutableList<TypeMeals>?) {
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