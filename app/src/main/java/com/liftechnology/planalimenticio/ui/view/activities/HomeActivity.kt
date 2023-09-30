package com.liftechnology.planalimenticio.ui.view.activities

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.framework.BaseActivity
import com.liftechnology.planalimenticio.ui.viewmodel.ShareViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author pelkidev
 * @date 21/08/2023
 * */
class HomeActivity : BaseActivity() {

    /* Variables iniciales */
    //private lateinit var binding: ActivityHomeBinding
    private val viewModel: ShareViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtiene argumentos de navegacion
        getArguments()

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
}