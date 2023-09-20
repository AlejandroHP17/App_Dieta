package com.liftechnology.planalimenticio.ui.view.category

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.databinding.FragmentCategoryBinding
import com.liftechnology.planalimenticio.framework.BaseFragment
import com.liftechnology.planalimenticio.ui.adapters.CategoriesAdapter
import com.liftechnology.planalimenticio.ui.adapters.CategoriesClickedListener
import com.liftechnology.planalimenticio.ui.viewmodel.ShareViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

/**
 * @author pelkidev
 * @date 21/08/2023
 * */
class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    // Vista al fragment base
    override fun getViewBinding() = FragmentCategoryBinding.inflate(layoutInflater)
    // Variable del viewModel
    private val shareModelMain: ShareViewModel by sharedViewModel()
    
    /* Variable para el recycler */
    private lateinit var adapterCategory: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /* Inicializa la vista con binding (databinding )y viewmodel */
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vmList = shareModelMain
        return binding.root
    }


    /** Inicializa la vista con parametros iniciales
     * @author pelkidev
     * @date 20/09/2023
     * */
    override fun setUpViews() {
        super.setUpViews()
        /* Toolbar: Se configura de manera inicial */
        binding.toolbarCategory.btnReturn.visibility = View.GONE
        binding.toolbarCategory.btnSearchBar.visibility = View.GONE
        binding.toolbarCategory.tvNameCategory.text = getString(R.string.toolbar_txt_categories)
    }


    /** Inicializa los observadores del viewmodel, para saber sus cambios
     * @author pelkidev
     * @date 20/09/2023
     * */
    override fun observeData() {
        super.observeData()
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                shareModelMain.dataFlow.collect{
                    setColorToolbar(it)
                    adapterCategory.submitList(it)
                    // Construye el recycler con el adaptador
                    binding.recyclerCards.adapter = adapterCategory

                }
            }
        }
    }


    /** Inicializa los listeners
     * @author pelkidev
     * @date 20/09/2023
     * */
    override fun listenersView() {
        super.listenersView()
        /* Maneja el click del card del adapter */
        adapterCategory = CategoriesAdapter(CategoriesClickedListener {
            // Navega al fragmento del listado de alimentos
            goToDetailsCategory(it)
        })
    }


    /** Método para darle color al toolbar de forma aleatoria
     * @author pelkidev
     * @date 20/09/2023
     * @param [it] lista de colores por servicio
     * */
    private fun setColorToolbar(it: List<CategoryResponse>) {
        val color = it[Random().nextInt(it.size)]
        shareModelMain.colorGeneral = color.startColor
        binding.toolbarCategory.toolbar.setBackgroundColor(Color.parseColor(color.startColor))
    }


    /** Metodo para realizar la navegacion al fragmento DetailsCategory
     * @author pelkidev
     * @date 20/09/2023
     * @param [data] objeto tipo categoryresponse que pasara como argumento
     * */
    private fun goToDetailsCategory(data:CategoryResponse) {
        /* Genera un argumento para enviarla a través de la navegación de fragmentos */
        val directions = CategoryFragmentDirections.actionNavigationCategoryToDetailsCategoryFragment(data)
        findNavController().navigate(directions)
    }
}
