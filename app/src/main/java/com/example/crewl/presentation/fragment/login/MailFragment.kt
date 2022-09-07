package com.example.crewl.presentation.fragment.login

import android.os.Bundle
import android.view.View
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.crewl.R
import com.example.crewl.core.BaseFragment
import com.example.crewl.databinding.FragmentMailBinding
import com.example.crewl.helper.ResourceHelper
import com.example.crewl.manager.NavigationManager.navigateSafely
import com.example.crewl.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MailFragment : BaseFragment<MailViewModel, FragmentMailBinding>() {
    private var binding: FragmentMailBinding by autoCleared()
    private val viewModel: MailViewModel by viewModels()

    override fun getViewModel(): Class<MailViewModel> = MailViewModel::class.java

    override fun getViewBinding(): FragmentMailBinding = FragmentMailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?, viewModel: MailViewModel, binding: FragmentMailBinding) {
        this@MailFragment.binding = binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.isUserRegistered.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { viewState ->
                    when (viewState) {
                        is ViewState.RenderSuccess ->
                            findNavController().navigateSafely(MailFragmentDirections.actionMailFragmentToSignInFragment())
                        is ViewState.RenderFailure -> {
                            binding.mailEditText.showKeyboardForInput()
                            binding.mailEditText.setInputError(viewState.throwable.message)
                            binding.mailEditText.setFocus()
                        }
                        is ViewState.Loading -> binding.continueButton.setLoading(isLoading = viewState.isLoading)
                        is ViewState.Empty -> {}
                    }
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun setUIAction() {
        setSpannableText()

        binding.continueButton.setOnClickListener {
            val mail: String = binding.mailEditText.getText()

            val isMailValid = ValidationUtils.isMailValid(mail = mail)

            isMailValid.then {
                viewModel.checkMail(mail = mail)
            }.letElse {
                binding.mailEditText.setInputError(getString(R.string.login_error_wrong_mail))
                binding.mailEditText.setFocus()
            }
        }

        /*
        binding.mEditText.setOnClickListener {
            val gradientDrawable = binding.mEditText.background as GradientDrawable
            gradientDrawable.mutate()

            val firstAnim = ValueAnimator.ofObject(ArgbEvaluator(), getColor(R.color.default_input_bg), getColor(R.color.error_placeholder))
            firstAnim.duration = 330
            firstAnim.addUpdateListener { valueAnimator: ValueAnimator ->
                gradientDrawable.setStroke(2, valueAnimator.animatedValue as Int)
            }
            firstAnim.start()

            val animation = ValueAnimator.ofInt(0, binding.mErrorText.height + 20)
            animation.duration = 330
            animation.addUpdateListener { valueAnimator: ValueAnimator ->
               binding.mErrorText.translationY = valueAnimator.animatedValue.toString().toFloat()
            }
            animation.start()
        }
         */
    }

    private fun setSpannableText() {
        binding.registerText.text = buildSpannedString {
            /* Don't have an account */
            color(ResourceHelper.getColor(R.color.white)) {
                font(ResourceHelper.getFont(R.font.inter_regular)) {
                    append(getString(R.string.dont_have_an_account))
                }
            }
            /* Get started */
            color(ResourceHelper.getColor(R.color.default_button_bg)) {
                font(ResourceHelper.getFont(R.font.inter_bold)) {
                    append(getString(R.string.get_started))
                }
            }
        }

        binding.termsOfServiceText.text = buildSpannedString {
            color(ResourceHelper.getColor(R.color.subtitle_gray_color)) {
                font(ResourceHelper.getFont(R.font.inter_medium)) {
                    append(getString(R.string.terms_of_policy_p1))
                }
            }
            color(ResourceHelper.getColor(R.color.white)) {
                font(ResourceHelper.getFont(R.font.inter_semibold)) {
                    append(getString(R.string.terms_of_policy))
                }
            }
            color(ResourceHelper.getColor(R.color.subtitle_gray_color)) {
                font(ResourceHelper.getFont(R.font.inter_medium)) {
                    append(getString(R.string.terms_of_policy_p2))
                }
            }
            color(ResourceHelper.getColor(R.color.white)) {
                font(ResourceHelper.getFont(R.font.inter_semibold)) {
                    append(getString(R.string.privacy_policy))
                }
            }
        }
    }
}