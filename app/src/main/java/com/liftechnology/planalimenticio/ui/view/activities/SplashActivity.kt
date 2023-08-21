package com.liftechnology.planalimenticio.ui.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import com.liftechnology.planalimenticio.databinding.ActivitySplashBinding
import com.liftechnology.planalimenticio.model.interfaces.ActivityListener
import com.liftechnology.planalimenticio.ui.viewextensions.toastActivity
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel

/**
 * @author pelkidev
 * @date 20/08/2023
 * */
class SplashActivity : AppCompatActivity(), ActivityListener {

    private lateinit var binding: ActivitySplashBinding

    private lateinit var viewModel: AllViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se vincula la vista y el viewmodel para utilizar databinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewModel = ViewModelProvider(this)[AllViewModel::class.java]
        binding.vm = viewModel

        // Se vincula del viewmodel el listener
        viewModel.listener = this

        // Inicializa la animacion del splash
        initAnimation()
    }

    /** Inicia la animacion de carga del splas, se muestra mientras carga el servicio
     * @author pelkidev
     * @date 20/08/2023
     * */
    private fun initAnimation() {
        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)
        binding.imageRotate.startAnimation(rotateAnimation)
    }

    /** Listener que indica que el servicio cargó correctament y navega los datos del response
     * @author pelkidev
     * @date 20/08/2023
     * */
    override fun onSuccessPrincipal(items: PrincipalResponse?) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("data", items)
        startActivity(intent)

        // Destruye el activity después de navegar
        finish()
    }

    /** Listener que indica que el servicio cargó incorrectament y muestra un mensaje de error
     * @author pelkidev
     * @date 20/08/2023
     * */
    override fun onError(errorCode: String) {
        toastActivity("The error was $errorCode")
    }
}