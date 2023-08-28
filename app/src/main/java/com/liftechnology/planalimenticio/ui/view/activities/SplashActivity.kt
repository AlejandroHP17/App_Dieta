package com.liftechnology.planalimenticio.ui.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.clase.MainViewModel
import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import com.liftechnology.planalimenticio.databinding.ActivitySplashBinding
import com.liftechnology.planalimenticio.model.di.homeModule
import com.liftechnology.planalimenticio.model.interfaces.ActivityListener
import com.liftechnology.planalimenticio.ui.viewextensions.initAnim
import com.liftechnology.planalimenticio.ui.viewextensions.toastActivity
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

/**
 * @author pelkidev
 * @date 20/08/2023
 * */
class SplashActivity : AppCompatActivity(), ActivityListener {

    /* Variables iniciales */
    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: AllViewModel
    private val viewModelMain by viewModel<MainViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la vista con binding y viewmodel
        initUI()

        // Inicializa la animacion del splash
        initAnimation()
    }

    /** Inicializa la vista con binding y viewmodel, ademas vincula el listener
     * @author pelkidev
     * @date 20/08/2023
     * */
    private fun initUI() {
        // Se vincula la vista y el viewmodel para utilizar databinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewModel = ViewModelProvider(this)[AllViewModel::class.java]
        binding.vm = viewModel

        startKoin {
            androidLogger()
            androidContext(this@SplashActivity)
            modules(homeModule)
        }

        //viewModelMain.doNetworkCall()

        // Se vincula del viewmodel el listener
        viewModel.listener = this
        viewModelMain.doNetworkCall()
    }


    /** Inicia la animacion de carga del splas, se muestra mientras carga el servicio
     * @author pelkidev
     * @date 20/08/2023
     * */
    private fun initAnimation() {
        binding.imageRotate.initAnim(this)
    }


    /** Listener que indica que el servicio cargó correctament y navega los datos del response
     * @author pelkidev
     * @date 20/08/2023
     * */
    override fun onSuccessPrincipal(items: PrincipalResponse?) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("data", items)
        startActivity(intent)

        /* Destruye componentes para limpiar memoria */
        binding.imageRotate.clearAnimation()
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