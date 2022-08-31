package com.example.crewl.presentation.activity

import android.os.Bundle
import android.view.View
import com.example.crewl.R
import com.example.crewl.core.BaseActivity
import com.example.crewl.databinding.ActivityMainBinding
import com.example.crewl.helpers.ConstantHelper.Companion.isFirstStart
import com.example.crewl.manager.AppManager
import com.google.firebase.FirebaseApp

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun getViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override val themeId: Int
        get() = R.style.DefaultTheme

    override fun onCreate(
        savedInstanceState: Bundle?,
        viewModel: MainViewModel,
        binding: ActivityMainBinding
    ) {
        this@MainActivity.binding = binding
        this@MainActivity.viewModel = viewModel

        AppManager.start()
        FirebaseApp.initializeApp(this@MainActivity)
    }

    override fun onStart() {
        super.onStart()

        if (isFirstStart) {
            isFirstStart = false

            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    override fun setStatusBarBackground() {}

    override fun getStatusBarHeight() {}
}