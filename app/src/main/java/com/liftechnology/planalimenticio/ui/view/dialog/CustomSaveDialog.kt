package com.liftechnology.planalimenticio.ui.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.DialogCustomSaveBinding
import com.liftechnology.planalimenticio.model.interfaces.DialogListener
import com.liftechnology.planalimenticio.ui.viewmodel.ShareViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CustomSaveDialog : DialogFragment() {

    /* Variables del viewModel*/
    private val shareModelMain: ShareViewModel by sharedViewModel()

    /* Variables auxiliares */
    private lateinit var bindingDialog :DialogCustomSaveBinding

    // Variable del listener
    var dialogListener: DialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val dialog = builder.create()


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

    private fun initView(){
        bindingDialog = DialogCustomSaveBinding.inflate(layoutInflater)
        bindingDialog.btnClose.setBackgroundColor(Color.parseColor(shareModelMain.colorGeneral))
        bindingDialog.btnSave.setBackgroundColor(Color.parseColor(shareModelMain.colorGeneral))
    }

    private fun initConfigurations(){
        // Cancelar el click fuera del dialogo
        dialog?.setCanceledOnTouchOutside(false)
    }

    private fun initListeners() {
        bindingDialog.apply {
            btnClose.setOnClickListener { dismiss() }

            btnSave.setOnClickListener {
                shareModelMain.saveTableWithName(requireActivity(), bindingDialog.txtDescriptionSave.text.toString())
                dialogListener?.onSaveTable()
            }
        }
    }

}