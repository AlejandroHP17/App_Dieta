package com.liftechnology.planalimenticio.ui.viewextensions

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment

/** Toast generic to Fragments */
fun Context.toastActivity(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastFragment(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}