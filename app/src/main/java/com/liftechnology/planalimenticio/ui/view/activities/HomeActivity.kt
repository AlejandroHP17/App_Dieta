package com.liftechnology.planalimenticio.ui.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import com.liftechnology.planalimenticio.databinding.ActivityHomeBinding
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel

/**
 * @author pelkidev
 * @date 21/08/2023
 * */
class HomeActivity : AppCompatActivity() {

    /* Variables iniciales */
    private lateinit var binding:ActivityHomeBinding
    private lateinit var viewModel: AllViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa la vista con binding y viewmodel
        initUI()

        // Obtiene argumentos de navegacion
        getArguments()

        // Gestiona la navegacion de fragmentos
        setNav()
    }

    /** Inicializa la vista con binding y viewmodel, ademas vincula el listener
     * @author pelkidev
     * @date 20/08/2023
     * */
    private fun initUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(this).get()
        binding.vm = viewModel
    }

    /** Obtiene los argumentos enviados del SplashActivity
     * @author pelkidev
     * @date 20/08/2023
     * @receiver [PrincipalResponse] Es el modelo de la respuesta del primer servicio
     * */
    private fun getArguments() {
        /* Postea el resultado en una variable en el viewmodel para comunicarla con los fragmentos*/
        val items = intent.getSerializableExtra("data") as? PrincipalResponse
        if (items != null) {
            viewModel.buildListCategory(items.result)
            //viewModel.argumentValue.value = items.result
        }
    }

    /** Inicializa la navegacion con el navView
     * @author pelkidev
     * @date 20/08/2023
     * */
    private fun setNav(){
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }
}