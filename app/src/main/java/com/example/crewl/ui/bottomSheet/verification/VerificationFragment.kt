package com.example.crewl.ui.bottomSheet.verification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crewl.R
import com.example.crewl.core.BaseFragment
import com.example.crewl.databinding.FragmentVerificationBinding

class VerificationFragment : BaseFragment<VerificationViewModel, FragmentVerificationBinding>() {
    private lateinit var binding: FragmentVerificationBinding

    override fun getViewModel(): Class<VerificationViewModel> = VerificationViewModel::class.java

    override fun getViewBinding(): FragmentVerificationBinding = FragmentVerificationBinding.inflate(layoutInflater)

    override fun onCreate(
        savedInstanceState: Bundle?,
        viewModel: VerificationViewModel,
        binding: FragmentVerificationBinding
    ) {
        this@VerificationFragment.binding = binding
    }

    override fun setUIAction() {
        TODO("Not yet implemented")
    }
}