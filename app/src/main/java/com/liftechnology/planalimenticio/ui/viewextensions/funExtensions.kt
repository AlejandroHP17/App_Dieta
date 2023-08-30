package com.liftechnology.planalimenticio.ui.viewextensions

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.liftechnology.planalimenticio.R

/** Toast generic to Fragments */
fun Context.toastActivity(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastFragment(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun AppCompatImageView.initAnim(context: Context){
    val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate)
    rotateAnimation.repeatCount = Animation.INFINITE
    this.startAnimation(rotateAnimation)
}