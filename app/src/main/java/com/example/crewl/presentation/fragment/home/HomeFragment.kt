package com.example.crewl.presentation.fragment.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.crewl.core.BaseFragment
import com.example.crewl.databinding.FragmentHomeBinding
import com.example.crewl.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    private var binding: FragmentHomeBinding by autoCleared()
    private val viewModel: HomeViewModel by viewModels()

    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?, viewModel: HomeViewModel, binding: FragmentHomeBinding) {
        this@HomeFragment.binding = binding
    }

    override fun setUIAction() {
        TODO("Not yet implemented")
    }

}