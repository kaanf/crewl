package com.example.crewl.presentation.bottomSheet.countryCode

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.view.View.*
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.crewl.R
import com.example.crewl.data.model.Country
import com.example.crewl.databinding.FragmentCountryCodeBinding
import com.example.crewl.helper.CountryCodeHelper
import com.example.crewl.helper.ResourceHelper.getDrawable
import com.example.crewl.presentation.bottomSheet.countryCode.adapter.CountryCodeAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CountryCodeFragment : BottomSheetDialogFragment() {
    companion object {
        internal const val TAG = "com.example.crewl.ui.bottomSheet.countryCode"
    }

    private lateinit var adapter: CountryCodeAdapter

    private lateinit var viewModel: CountryCodeViewModel
    private lateinit var behavior: BottomSheetBehavior<View>

    private var binding: FragmentCountryCodeBinding? = null

    private var screenHeight: Int = 0
    private var screenWidth: Int = 0

    override fun getTheme(): Int = R.style.BottomSheetDialogNormal

    override fun onAttach(context: Context) {
        val displayMetrics = Resources.getSystem().displayMetrics

        screenHeight = displayMetrics.heightPixels
        screenWidth = displayMetrics.widthPixels

        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = inflate(context, R.layout.fragment_country_code, null)

        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheet.setContentView(view)

        behavior = BottomSheetBehavior.from(view.parent as View)
        behavior.apply {
            skipCollapsed = true
            behavior.maxHeight = (screenHeight * 0.9).toInt()
        }

        return bottomSheet
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflate(context, R.layout.fragment_country_code, null)

        binding = FragmentCountryCodeBinding.bind(view).apply {
            countryCodeContainer.setOnClickListener { dismissAllowingStateLoss() }
            adapter = CountryCodeAdapter {
                val data = mutableListOf(it.phoneCode, it.countryName)
                findNavController().previousBackStackEntry?.savedStateHandle?.set("data", data)

                dismiss()
            }
            countryPickerList.adapter = adapter

            context?.let { context ->
                val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

                getDrawable(R.drawable.divider_recycler_view_gray)?.let {
                    itemDecorator.setDrawable(it)
                    countryPickerList.addItemDecoration(itemDecorator)
                }
            }
        }

        binding?.searchEditText?.setOnTouchListener { searchView, event ->
            if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_DOWN) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                searchView.requestFocus()
            }
            false
        }

        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = getViewModel()

        binding?.searchEditText?.doOnTextChanged { text, _, _, _ ->
            viewModel.setQuery(text.toString().ifBlank { null })
        }

        viewModel.getCountries().observe(viewLifecycleOwner) { countries ->
            checkProgressBar(countries)
            countries?.let { adapter.setCountries(it) }
        }

        viewModel.loadCountries()
    }

    private fun checkProgressBar(list: List<Country>) {
        if(list.size.compareTo(0) != 0) {
            binding?.let {
                it.progressBar.visibility = GONE
            }
        }
    }

    private fun getViewModel(): CountryCodeViewModel {
        val factory = CountryCodeViewModelFactory.Factory(
            CountryCodeHelper.getCountryRepository()
        )
        return ViewModelProvider(this, factory)[CountryCodeViewModel::class.java]
    }

    override fun onResume() {
        dialog?.window?.apply {
            setBackgroundDrawable(null)
            val params = attributes.also {
                it.width = WindowManager.LayoutParams.MATCH_PARENT
                it.height = WindowManager.LayoutParams.MATCH_PARENT
            }
            attributes = params
        }

        super.onResume()
    }

    override fun onDestroyView() {
        binding?.countryPickerList?.adapter = null
        binding = null

        super.onDestroyView()
    }
}