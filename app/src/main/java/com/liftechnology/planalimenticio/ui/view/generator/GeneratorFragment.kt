package com.liftechnology.planalimenticio.ui.view.generator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.liftechnology.planalimenticio.databinding.FragmentGeneratorBinding
import com.liftechnology.planalimenticio.framework.BaseFragment
import com.liftechnology.planalimenticio.ui.utils.StackConstant
import com.liftechnology.planalimenticio.ui.viewmodel.ShareViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class GeneratorFragment : BaseFragment<FragmentGeneratorBinding>() {
    override fun getViewBinding() = FragmentGeneratorBinding.inflate(layoutInflater)

    /* Variable del viewModel */
    private val shareModelMain: ShareViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }


    /** Inicializa la vista con parametros iniciales
     * @author pelkidev
     * @date 20/09/2023
     * */
    override fun setUpViews() {
        super.setUpViews()
        /* Toolbar: Se configura de manera inicial */
    }

    override fun listenersView() {
        super.listenersView()
        binding.apply {
            btnAdd.setOnClickListener { navToDestination(StackConstant.TABLE) }
        }

    }
}