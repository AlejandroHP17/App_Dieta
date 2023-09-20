package com.liftechnology.planalimenticio.ui.viewextensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
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

fun toastSuccess(message: String, activity: Activity,time:Long=2500) {
    val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.AlertDialogRoundBase)
    val layoutView: View? = activity.layoutInflater.inflate(R.layout.dialog_custom_success_toast, null)
    val ivClose: AppCompatImageView? = layoutView?.findViewById(R.id.iv_close)
    val tvMessage: AppCompatTextView? = layoutView?.findViewById(R.id.tv_mensaje)
    tvMessage?.text = message
    dialogBuilder.setView(layoutView)
    val alertDialog: AlertDialog = dialogBuilder.create()
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.window?.setGravity(Gravity.TOP)
    alertDialog.show()
    ivClose?.setOnClickListener { alertDialog.dismiss() }
    Handler(Looper.getMainLooper()).postDelayed({
        try {
            alertDialog.dismiss()
        } catch (_: Exception) { // NOTHING
        }
    }, time)
}

fun toastFailed(message: String?, activity: Activity, time: Long = 5000)
{
    val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity,R.style.AlertDialogRoundBase)
    val layoutView: View? =activity.layoutInflater.inflate(R.layout.dialog_custom_failed_toast, null)
    val ivClose: AppCompatImageView? = layoutView?.findViewById(R.id.iv_close)
    val tvMessage: AppCompatTextView? = layoutView?.findViewById(R.id.tv_mensaje)
    tvMessage?.text = message
    dialogBuilder.setView(layoutView)
    val alertDialog: AlertDialog = dialogBuilder.create()
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.window?.setGravity(Gravity.TOP)
    alertDialog.show()
    ivClose?.setOnClickListener { alertDialog.dismiss() }
    Handler(Looper.getMainLooper()).postDelayed({
        try {
            if (!activity.isDestroyed) alertDialog.dismiss()
        } catch (e: Exception) { // NOTHING
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
    this.text = currentText
}