package com.liftechnology.planalimenticio.ui.utils

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.DialogCustomSelectBinding
import com.liftechnology.planalimenticio.model.dataclass.TypeTable
import com.liftechnology.planalimenticio.model.interfaces.DialogListener
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel
import com.liftechnology.planalimenticio.ui.viewmodel.VMTable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CustomSelectDialog : DialogFragment() {

    private val viewModelMain: AllViewModel by sharedViewModel()
    var dialogListener: DialogListener? = null
    private val viewModelTable: VMTable by sharedViewModel()

    private var argTitle: String? = null
    private var argDescription: String? = null
    private var argNumber: Int? = null

    private lateinit var bindingDialog :DialogCustomSelectBinding

    companion object {
        fun newInstance(title: String, body: String, number: Int): CustomSelectDialog {
            val fragment = CustomSelectDialog()
            val args = Bundle()
            args.putString("title", title)
            args.putString("body", body)
            args.putInt("number", number)
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
        bindingDialog.btnClose.setBackgroundColor(Color.parseColor(viewModelMain.colorGeneral))
    }

    private fun initConfigurations(){
        dialog?.setCanceledOnTouchOutside(false)
    }

    private fun initListeners(){
        bindingDialog.apply {
            btnClose.setOnClickListener {
                dismiss()
            }

            btnAccept.setOnClickListener {
                when (argDescription){
                    ValidateTextDialogSelect.PORTION -> dialogListener?.onDataReceivedPortion(argNumber!!)
                    ValidateTextDialogSelect.QUANTITY -> dialogListener?.onDataReceivedQuantity(argNumber!!)
                    ValidateTextDialogSelect.MEALS -> dialogListener?.onDataReceivedMeals(argNumber!!)
                }

                //val typeTable = TypeTable(listTable, Pair(titleNumberMeals, data))
                //viewModelTable.updateTable(requireContext(), typeTable)
                dismiss()
            }

            btnPlus.setOnClickListener {
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

            btnMinus.setOnClickListener {
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
        }
    }
}