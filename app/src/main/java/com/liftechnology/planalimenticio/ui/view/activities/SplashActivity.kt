package com.liftechnology.planalimenticio.ui.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.databinding.ActivitySplashBinding
import com.liftechnology.planalimenticio.framework.CoroutineScopeManager
import com.liftechnology.planalimenticio.model.interfaces.SplashListener
import com.liftechnology.planalimenticio.ui.viewextensions.initAnim
import com.liftechnology.planalimenticio.ui.viewextensions.toastFailed
import com.liftechnology.planalimenticio.ui.viewmodel.ShareViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author pelkidev
 * @date 20/08/2023
 * */
class SplashActivity : AppCompatActivity(), SplashListener {

    /* Variables iniciales */
    private lateinit var binding: ActivitySplashBinding
    private val viewModel: ShareViewModel by viewModel()

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
        /* Se vincula la vista y el viewmodel para utilizar databinding */
        binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        viewModel.listener = this
        setContentView(binding.root)
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
    override fun onSuccessPrincipal(items: List<CategoryResponse?>) {
        // Convertir la lista a un JSON usando Gson
        val gson = Gson()
        val itemsJson = gson.toJson(items)

        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("data", itemsJson)
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
        CoroutineScopeManager().scopeMainThread.launch {toastFailed(errorCode, this@SplashActivity)}
        binding.imageRotate.clearAnimation()
    }
}