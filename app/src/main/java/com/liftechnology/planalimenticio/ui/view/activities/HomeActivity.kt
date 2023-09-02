package com.liftechnology.planalimenticio.ui.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.databinding.ActivityHomeBinding
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author pelkidev
 * @date 21/08/2023
 * */
class HomeActivity : AppCompatActivity() {

    /* Variables iniciales */
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: AllViewModel by viewModel()

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
        binding = ActivityHomeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        setContentView(binding.root)
    }

    /** Obtiene los argumentos enviados del SplashActivity
     * @author pelkidev
     * @date 20/08/2023
     * */
    private fun getArguments() {
        val itemsJson = intent.getStringExtra("data")

        if (itemsJson != null) {
            val gson = Gson()
            /* Usar TypeToken para deserializar la lista de CategoryResponse */
            val itemsType = object : TypeToken<List<CategoryResponse>>() {}.type
            val items: List<CategoryResponse> = gson.fromJson(itemsJson, itemsType)
            // Postea el resultado en una variable en el viewmodel para comunicarla con los fragmentos
            viewModel.buildListCategory(items)
        }
    }


    /** Inicializa la navegacion con el navView
     * @author pelkidev
     * @date 20/08/2023
     * */
    private fun setNav() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }
}