package com.liftechnology.planalimenticio.ui.viewextensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.liftechnology.planalimenticio.R

/** Toast generic to Fragments */
fun Context.toastActivity(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastFragment(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toastSuccess(message: String, activity: Activity,time:Long=2500)
{
    val dialogBuilder: AlertDialog.Builder =
        AlertDialog.Builder(activity, R.style.AlertDialogRoundBase)
    var layoutView: View? = activity?.layoutInflater?.inflate(R.layout.dialog_custom_success_toast, null)
    val ivClose: AppCompatImageView? = layoutView?.findViewById(R.id.iv_close)
    val tv_mensaje: AppCompatTextView? = layoutView?.findViewById(R.id.tv_mensaje)
    tv_mensaje?.text = message
    dialogBuilder.setView(layoutView)
    var alertDialog: AlertDialog = dialogBuilder.create()
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.window?.setGravity(Gravity.TOP)
    alertDialog.show()
    ivClose?.setOnClickListener { alertDialog.dismiss() }
    Handler().postDelayed({
        try {
            alertDialog.dismiss()
        } catch (e: Exception) {
        }
    }, time)
}

fun Fragment.toastFailed(message: String?, activity: Activity, time: Long = 5000)
{
    val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity,R.style.AlertDialogRoundBase)
    var layoutView: View? = activity?.layoutInflater?.inflate(R.layout.dialog_custom_failed_toast, null)

    val ivClose: AppCompatImageView? = layoutView?.findViewById(R.id.iv_close)

    val tv_mensaje: AppCompatTextView? = layoutView?.findViewById(R.id.tv_mensaje)

    tv_mensaje?.text = message

    dialogBuilder.setView(layoutView)
    var alertDialog: AlertDialog = dialogBuilder.create()
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.window?.setGravity(Gravity.TOP)
    alertDialog.show()
    ivClose?.setOnClickListener { alertDialog.dismiss() }
    Handler().postDelayed({
        try {
            if (!activity.isDestroyed) alertDialog.dismiss()
        } catch (e: Exception) {
        }
    }, time)

}

fun AppCompatImageView.initAnim(context: Context){
    val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate)
    rotateAnimation.repeatCount = Animation.INFINITE
    this.startAnimation(rotateAnimation)
}

fun TextView.concatTextWithAdditional(text: Pair<String, Int>) {
    val currentText = text.first + " -> " + text.second.toString()
    val concatenatedText = "$currentText -> $text"
    this.text = currentText
}