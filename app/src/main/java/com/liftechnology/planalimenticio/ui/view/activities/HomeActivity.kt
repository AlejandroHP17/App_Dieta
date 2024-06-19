package com.liftechnology.planalimenticio.ui.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.databinding.ActivityHomeBinding
import com.liftechnology.planalimenticio.ui.viewmodel.ShareViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author pelkidev
 * @date 21/08/2023
 * */
class HomeActivity : AppCompatActivity() {

    /* Variables iniciales */
    private var binding: ActivityHomeBinding? = null
    private val viewModel: ShareViewModel by viewModel()

    private var navController  : NavController? = null
    private var navView : BottomNavigationView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializa la vista con binding y viewmodel
        initUI()

        // Obtiene argumentos de navegacion
        getArguments()

        // Inicia la navegación
        initNavigation()

    }

    /** Inicializa la vista con binding y viewmodel, ademas vincula el listener
     * @author pelkidev
     * @date 20/08/2023
     * */
    private fun initUI() {
        /* Se vincula la vista y el viewmodel para utilizar databinding */
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
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

    /** Inicializa la navegación con el navView
     * @author pelkidev
     * @date 19/06/2024
     * */
    private fun initNavigation(){
        // Inicializa el NavController
        navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Inicializa la vista de navegación (BottomNavigationView)
        navView = binding?.navView

        // Configura la navegación con la vista de navegación
        NavigationUI.setupWithNavController(navView!!, navController!!)

        binding?.navView?.apply {
            navController?.let { navController ->
                NavigationUI.setupWithNavController(this, navController)
                setOnItemSelectedListener { item ->
                    NavigationUI.onNavDestinationSelected(item, navController)
                }

                setOnItemReselectedListener {
                    val selectedMenuItem = navController.graph.findNode(it.itemId) as NavGraph
                    selectedMenuItem.let {item ->
                        navController.popBackStack(item.startDestinationId, false)
                    }
                }
            }
        }
    }

    fun navToDestination(id : Int){
        navView?.selectedItemId = id
        navView?.performClick()
    }
}