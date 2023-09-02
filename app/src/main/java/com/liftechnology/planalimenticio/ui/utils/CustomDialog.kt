package com.liftechnology.planalimenticio.ui.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.DialogCustomDetailBinding

class CustomDialog : DialogFragment() {

    companion object {
        fun newInstance(title: String, body: String): CustomDialog {
            val fragment = CustomDialog()
            val args = Bundle()
            args.putString("title", title)
            args.putString("body", body)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())

        val dialog = builder.create()

        // Recuperar los argumentos
        val argumento1 = arguments?.getString("title")
        val argumento2 = arguments?.getString("body")

        // Configura el estilo de animación personalizado
        dialog.window?.attributes?.windowAnimations = R.style.CustomDialogAnimation

        // Infla y configura tu contenido personalizado en el cuadro de diálogo
        val bindingDialog = DialogCustomDetailBinding.inflate(layoutInflater)
        bindingDialog.txtTitle.text = argumento1
        bindingDialog.txtDescription.text = argumento2

        dialog.setCanceledOnTouchOutside(false)

        // Configura la vista personalizada en el cuadro de diálogo
        dialog.setView(bindingDialog.root)

        bindingDialog.btnClose.setOnClickListener { dismiss() }
        builder.setPositiveButton("CERRAR") { _, _ ->
            // Realizar la acción de cierre aquí
            dismiss() // Cierra el cuadro de diálogo
        }

        return dialog
    }
}