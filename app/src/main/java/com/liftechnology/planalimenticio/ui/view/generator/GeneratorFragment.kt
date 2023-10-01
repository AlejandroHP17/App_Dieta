package com.liftechnology.planalimenticio.ui.view.generator

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.core.view.get
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.databinding.FragmentGeneratorBinding
import com.liftechnology.planalimenticio.framework.BaseFragment
import com.liftechnology.planalimenticio.model.dataclass.ListTypeTable
import com.liftechnology.planalimenticio.ui.adapters.GeneratorAdapter
import com.liftechnology.planalimenticio.ui.adapters.GeneratorClickedListener
import com.liftechnology.planalimenticio.ui.utils.StackConstant
import com.liftechnology.planalimenticio.ui.viewextensions.toastFailed
import com.liftechnology.planalimenticio.ui.viewmodel.ShareViewModel
import com.liftechnology.planalimenticio.ui.viewmodel.VMGenerator
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class GeneratorFragment : BaseFragment<FragmentGeneratorBinding>() {
    override fun getViewBinding() = FragmentGeneratorBinding.inflate(layoutInflater)

    /* Variable del viewModel */
    private val shareModelMain: ShareViewModel by sharedViewModel()
    private val vmGenerator: VMGenerator by sharedViewModel()

    /* Variable para el recycler */
    private lateinit var adapterGenerator: GeneratorAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
/* Inicializa la vista con binding (databinding )y viewmodel */
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vmGenerator = vmGenerator

        return binding.root
    }


    /** Inicializa la vista con parametros iniciales
     * @author pelkidev
     * @date 20/09/2023
     * */
    override fun setUpViews() {
        super.setUpViews()
        /* Toolbar: Se configura de manera inicial */
        binding.toolbarGenerator.tvNameCategory.text = getString(R.string.toolbar_txt_generator)
        binding.toolbarGenerator.toolbar.setBackgroundColor(Color.parseColor(shareModelMain.colorGeneral))
        binding.llEmpty.visibility = View.VISIBLE
        binding.llList.visibility = View.GONE
    }

    override fun listenersView() {
        super.listenersView()
        binding.apply {
            btnAdd.setOnClickListener { navToDestination(StackConstant.TABLE) }
        }

        adapterGenerator = GeneratorAdapter(GeneratorClickedListener {item, click, position ->
            when(click){
                "Data" -> {}
                "Delete" -> {vmGenerator.deleteItemTable(requireContext(),position)}

            }
        })

    }

    override fun observeData() {
        super.observeData()
        vmGenerator.listGenerator.observe(viewLifecycleOwner){
            adapterGenerator.submitList(it)
            // Construye el recycler con el adaptador
            binding.recyclerCardsList.adapter = adapterGenerator
            binding.llEmpty.visibility = View.GONE
            binding.llList.visibility = View.VISIBLE
        }
        vmGenerator.listGeneratorEmpty.observe(viewLifecycleOwner){
            binding.llEmpty.visibility = View.VISIBLE
            binding.llList.visibility = View.GONE
            toastFailed(it, requireActivity())
        }




    }

    override fun initData() {
        super.initData()
        vmGenerator.getListTable(requireContext())
    }

}