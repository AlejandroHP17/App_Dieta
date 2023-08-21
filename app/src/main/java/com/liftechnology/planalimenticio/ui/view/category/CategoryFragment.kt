package com.liftechnology.planalimenticio.ui.view.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.data.network.models.response.FoodResponse
import com.liftechnology.planalimenticio.databinding.FragmentCategoryBinding
import com.liftechnology.planalimenticio.ui.adapters.CategoriesAdapter
import com.liftechnology.planalimenticio.ui.adapters.CategoriesClickedListener
import com.liftechnology.planalimenticio.ui.viewextensions.toastFragment
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel
import com.liftechnology.planalimenticio.ui.viewmodel.VMCategory

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private val viewModelList: AllViewModel by activityViewModels()
    private val vmCategory: VMCategory by viewModels()

    /** Variable to recycler */
    private lateinit var adapterCategory: CategoriesAdapter
    private var listCategory: MutableList<CategoryResponse>? = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.vmList = viewModelList

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
        initListeners()
    }

    private fun initView(){
        binding.toolbarCategory.btnReturn.visibility = View.GONE
        binding.toolbarCategory.btnSearchBar.visibility = View.GONE
        binding.toolbarCategory.tvNameCategory.text = "Categorias"
    }
    private fun initListeners() {
        adapterCategory = CategoriesAdapter(CategoriesClickedListener {
            vmCategory.getItemVegetable(it.url)
            navigate(Pair(it.startColor, it.endColor),it.url,it.category)
        })
    }

    private fun navigate(color: Pair<String, String>, url:String, category:String) {

        val data = arrayOf<String>(color.first, color.second, url, category)
        val directions = CategoryFragmentDirections.actionNavigationCategoryToDetailsCategoryFragment(data)
        findNavController().navigate(directions)
    }

    private fun initObservers() {
        viewModelList.argumentValue.observe(viewLifecycleOwner, handlerChange())
        vmCategory.getVegetable.observe(viewLifecycleOwner, handlerVegetable())
    }


    private fun handlerVegetable(): (List<FoodResponse>) -> Unit = { items ->

        items.forEach {
            println(it.alimento)
        }
    }


    private fun handlerChange(): (List<CategoryResponse>) -> Unit = { items ->
        // Call the method to modify the view
        listCategory = mutableListOf()
        val list: MutableList<CategoryResponse> = mutableListOf()
        items?.forEach { it ->
            list.add(it)
        }

        // Verifica si la lista es null y, en caso afirmativo, crea una colección vacía
        listCategory?.addAll(list)
        // Add the items to the adapter
        adapterCategory.submitList(listCategory)
        // Build the adapter
        binding.recyclerCards.adapter = adapterCategory
    }




}
