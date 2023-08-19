package com.liftechnology.planalimenticio.ui.view.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.data.local.ModelCardList
import com.liftechnology.planalimenticio.data.network.models.response.CategoryResponse
import com.liftechnology.planalimenticio.data.network.models.response.VegetableResponse
import com.liftechnology.planalimenticio.databinding.FragmentCategoryBinding
import com.liftechnology.planalimenticio.databinding.FragmentDetailsCategoryBinding
import com.liftechnology.planalimenticio.ui.adapters.CategoriesAdapter
import com.liftechnology.planalimenticio.ui.adapters.CategoriesClickedListener
import com.liftechnology.planalimenticio.ui.adapters.FoodClickedListener
import com.liftechnology.planalimenticio.ui.adapters.GeneralAdapter
import com.liftechnology.planalimenticio.ui.viewextensions.toastFragment
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel
import com.liftechnology.planalimenticio.ui.viewmodel.VMCategory


class DetailsCategoryFragment : Fragment() {

    private lateinit var binding: FragmentDetailsCategoryBinding
    private val vmCategory: VMCategory by viewModels()

    /** Variable to recycler */
    private lateinit var adapterCategory: GeneralAdapter
    private var listCategory: MutableList<ModelCardList>? = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailsCategoryBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.vmList = vmCategory

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
        vmCategory.getItemVegetable(DetailsCategoryFragmentArgs.fromBundle(requireArguments()).color[2])
    }



    private fun initListeners() {
        adapterCategory = GeneralAdapter(FoodClickedListener {
        })
    }

    private fun initObservers() {
        vmCategory.getVegetable.observe(viewLifecycleOwner, handlerVegetable())
    }

    private fun handlerVegetable():(List<VegetableResponse>) -> Unit = { items ->
        val color = DetailsCategoryFragmentArgs.fromBundle(requireArguments()).color

        val list: MutableList<ModelCardList> = mutableListOf()
        items.forEach { it ->
            list.add(ModelCardList(
                name = it.alimento,
                quantity = it.cantidad_sugerida,
                unit = it.unidad,
                weight = it.peso_neto_g.toString()+ " g",
                startColor = color[0],
                endColor = color[1]
            ))
        }

        // Verifica si la lista es null y, en caso afirmativo, crea una colección vacía
        listCategory?.addAll(list)
        // Add the items to the adapter
        adapterCategory.submitList(listCategory)
        // Build the adapter
        binding.recyclerCardsList.adapter = adapterCategory

    }


}