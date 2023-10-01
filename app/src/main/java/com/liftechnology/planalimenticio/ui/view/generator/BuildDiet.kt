package com.liftechnology.planalimenticio.ui.view.generator

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.FragmentBuildDietBinding
import com.liftechnology.planalimenticio.databinding.FragmentGeneratorBinding
import com.liftechnology.planalimenticio.framework.BaseFragment
import com.liftechnology.planalimenticio.ui.viewmodel.ShareViewModel
import com.liftechnology.planalimenticio.ui.viewmodel.VMBuildDiet
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BuildDiet : BaseFragment<FragmentBuildDietBinding>() {

    override fun getViewBinding() = FragmentBuildDietBinding.inflate(layoutInflater)

    /* Variable del viewModel */
    private val shareModelMain: ShareViewModel by sharedViewModel()
    private val vmBuildDiet : VMBuildDiet by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vmBuildDiet = vmBuildDiet
        return binding.root
    }

    /** Inicializa la vista con parametros iniciales
     * @author pelkidev
     * @date 20/09/2023
     * */
    override fun setUpViews() {
        super.setUpViews()
        /* Toolbar: Se configura de manera inicial */
        binding.toolbarBuildDiet.tvNameCategory.text = getString(R.string.toolbar_txt_buildDiet)
        binding.toolbarBuildDiet.toolbar.setBackgroundColor(Color.parseColor(shareModelMain.colorGeneral))
    }


}