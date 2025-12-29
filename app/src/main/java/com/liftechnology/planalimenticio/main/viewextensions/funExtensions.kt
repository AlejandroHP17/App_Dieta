package com.liftechnology.planalimenticio.main.viewextensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.DialogCustomFailedToastBinding
import com.liftechnology.planalimenticio.databinding.DialogCustomSuccessToastBinding

/** Toast generic to Fragments */
fun Context.toastActivity(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastFragment(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun toastSuccess(message: String, activity: Activity,time:Long=2500) {
    val dialogBuilder = AlertDialog.Builder(activity, R.style.AlertDialogRoundBase)
    val dialogView = DialogCustomSuccessToastBinding.inflate(activity.layoutInflater)

    dialogView.tvMensaje.text = message
    dialogBuilder.setView(dialogView.root)

    val alertDialog = dialogBuilder.create()
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.window?.setGravity(Gravity.TOP)
    alertDialog.show()

    dialogView.ivClose.setOnClickListener { alertDialog.dismiss() }

    Handler(Looper.getMainLooper()).postDelayed({
        if (!activity.isDestroyed) alertDialog.dismiss()
    }, time)
}

fun toastFailed(message: String?, activity: Activity, time: Long = 5000)
{
    val dialogBuilder = AlertDialog.Builder(activity, R.style.AlertDialogRoundBase)
    val dialogView = DialogCustomFailedToastBinding.inflate(activity.layoutInflater)

    dialogView.tvMensaje.text = message
    dialogBuilder.setView(dialogView.root)

    val alertDialog = dialogBuilder.create()
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.window?.setGravity(Gravity.TOP)
    alertDialog.show()

    dialogView.ivClose.setOnClickListener { alertDialog.dismiss() }

    Handler(Looper.getMainLooper()).postDelayed({
        if (!activity.isDestroyed) alertDialog.dismiss()
    }, time)
}

fun AppCompatImageView.initAnim(context: Context){
    val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate)
    rotateAnimation.repeatCount = Animation.INFINITE
    this.startAnimation(rotateAnimation)
}

fun TextView.concatTextWithAdditional(text: Pair<String, Int>) {
    val currentText = text.first + " -> " + text.second.toString()
    this.text = currentText
}