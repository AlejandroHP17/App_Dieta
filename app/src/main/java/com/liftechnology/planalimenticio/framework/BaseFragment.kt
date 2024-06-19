package com.liftechnology.planalimenticio.framework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author pelkidev
 * @date 20/09/2023
 * */
abstract class BaseFragment<VBinding : ViewBinding> : Fragment() {

    open var useSharedViewModel: Boolean = false


    protected lateinit var binding: VBinding
    protected abstract fun getViewBinding(): VBinding

    private val disposableContainer = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgumentsNavigation()
        setUpViews()
        initData()
        listenersView()
        observeData()
    }

    open fun setUpViews() {
        //EMPTY
    }

    open fun listenersView(){
        //EMPTY
    }

    open fun observeData() {
        //EMPTY
    }

    open fun getArgumentsNavigation(){
        //EMPTY
    }

    open fun initData(){
        //EMPTY
    }

    private fun init() {
        binding = getViewBinding()
    }

    fun Disposable.addToContainer() = disposableContainer.add(this)

    override fun onDestroyView() {
        disposableContainer.clear()
        super.onDestroyView()
    }

    fun navToDestination(id: Int){
        //(this.requireActivity() as BaseActivity).navToDestination(id)
    }


}