package com.liftechnology.planalimenticio.ui.view.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import com.liftechnology.planalimenticio.databinding.ActivityHomeBinding
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding
    //private val viewModel: AllViewModel by viewModels()
    private lateinit var viewModel: AllViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** First Configurations*/
        // Set UI with fragments
        setUIWithFragments()
        getArguments()
        setNav()

    }

    private fun getArguments() {
        val items = intent.getSerializableExtra("data") as? PrincipalResponse
        if (items != null) {
            viewModel.argumentValue.value = items.result
        }
    }

    /** Declare and link up the view with the view-model */
    private fun setUIWithFragments() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(this)[AllViewModel::class.java]
        binding.vm = viewModel
    }

    private fun setNav(){
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_category, R.id.navigation_table, R.id.navigation_generator
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}