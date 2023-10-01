package com.liftechnology.planalimenticio.framework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.ActivityHomeBinding
import com.liftechnology.planalimenticio.ui.utils.StackConstant

open class BaseActivity : AppCompatActivity() {
    private lateinit var navController  : NavController
    private lateinit var navView : BottomNavigationView

    private lateinit var binding: ActivityHomeBinding

    /** Inicializa la vista con binding y viewmodel, ademas vincula el listener
     * @author pelkidev
     * @date 20/08/2023
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** Inicializa la navegación con el navView
         * @author pelkidev
         * @date 20/08/2023
         * */

        // Inicializa el NavController
        navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Inicializa la vista de navegación (BottomNavigationView)
        navView = findViewById(R.id.nav_view)

        // Configura la navegación con la vista de navegación
        NavigationUI.setupWithNavController(navView, navController)

        binding.navView.apply {
            navController.let { navController ->
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
        navView.selectedItemId = id
        navView.performClick()
    }
}