package com.liftechnology.planalimenticio.ui.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.liftechnology.planalimenticio.R
import com.liftechnology.planalimenticio.data.network.models.response.PrincipalResponse
import com.liftechnology.planalimenticio.databinding.ActivitySplashBinding
import com.liftechnology.planalimenticio.model.interfaces.ActivityListener
import com.liftechnology.planalimenticio.ui.viewextensions.toastActivity
import com.liftechnology.planalimenticio.ui.viewextensions.toastFragment
import com.liftechnology.planalimenticio.ui.viewmodel.AllViewModel

class SplashActivity : AppCompatActivity(), ActivityListener {

    private lateinit var binding: ActivitySplashBinding
    //private val viewModel: AllViewModel by viewModels()
    private lateinit var viewModel: AllViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding = SplashActivityBinding.inflate(layoutInflater)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewModel = ViewModelProvider(this)[AllViewModel::class.java]
        binding.vm = viewModel
        viewModel.listener = this

        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)

        viewModel.getFirstService()
        binding.imageRotate.startAnimation(rotateAnimation)

    }

    override fun onSuccessPrincipal(items: PrincipalResponse?) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("data", items)
        startActivity(intent)
        finish()
    }

    override fun onError(errorCode: Int) {
        toastActivity("The error was $errorCode")
    }
}