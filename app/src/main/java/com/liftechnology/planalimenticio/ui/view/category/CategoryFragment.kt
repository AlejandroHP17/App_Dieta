package com.liftechnology.planalimenticio.ui.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.FragmentCategoryBinding
import com.liftechnology.planalimenticio.ui.adapters.CategoriesAdapter
import com.liftechnology.planalimenticio.ui.adapters.CategoriesClickedListener
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel
import com.liftechnology.planalimenticio.ui.viewmodel.VMCategory
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * @author pelkidev
 * @date 21/08/2023
 * */
class CategoryFragment : Fragment() {
    /* Variables iniciales */
    private lateinit var binding: FragmentCategoryBinding
    //private val viewModelMain: AllViewModel by activityViewModels()
    private val viewModelMain: AllViewModel by sharedViewModel()


    /* Variable para el recycler */
    private lateinit var adapterCategory: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /* Inicializa la vista con binding y viewmodel */
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vmList = viewModelMain
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicializa los elementos de la vista
        initView()
        // Inicializa los observers
        initObservers()
        // Inicializa los listeners
        initListeners()



    }

    /** Inicializa la vista con parametros iniciales
     * @author pelkidev
     * @date 21/08/2023
     * */
    private fun initView(){
        /* Toolbar: Se configura de manera inicial */
        binding.toolbarCategory.btnReturn.visibility = View.GONE
        binding.toolbarCategory.btnSearchBar.visibility = View.GONE
        binding.toolbarCategory.tvNameCategory.text = getString(R.string.toolbar_txt_categories)
    }

    /** Inicializa los observadores del viewmodel, para saber sus cambios
     * @author pelkidev
     * @date 21/08/2023
     * */
    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch{

            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModelMain.dataFlow.collect{
                    adapterCategory.submitList(it)
                    // Construye el recycler con el adaptador
                    binding.recyclerCards.adapter = adapterCategory

                }
            }

        }
    }

    /** Inicializa los listeners
     * @author pelkidev
     * @date 21/08/2023
     * */
    private fun initListeners() {
        /* Maneja el click del card del adapter */
        adapterCategory = CategoriesAdapter(CategoriesClickedListener {
            // Navega al fragmento del listado de alimentos
            goToDetailsCategory(Pair(it.startColor, it.endColor),it.url,it.category)
        })
    }

    /** Metodo para realizar la navegacion al fragmento DetailsCategory
     * @author pelkidev
     * @date 21/08/2023
     * @param [color] Valores para generar la gradiente al construir el adapter
     * @param [url] endPoint para consultar el servicio de la categoria
     * @param [category] Valor con el nombre de la categoria
     * */
    private fun goToDetailsCategory(color: Pair<String, String>, url:String, category:String) {
        /* Genera un argumento para enviarla a través de la navegación de fragmentos */
        viewModelMain.changeStateLoader(true)
        val data = arrayOf(color.first, color.second, url, category)
        val directions = CategoryFragmentDirections.actionNavigationCategoryToDetailsCategoryFragment(data)
        findNavController().navigate(directions)
    }

}
