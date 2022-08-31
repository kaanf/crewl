package com.example.crewl.ui.fragment.login

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.core.text.inSpans

class LoginFragmentHelper {
    companion object {
        fun SpannableStringBuilder.font(
            typeface: Typeface? = null,
            builderAction: SpannableStringBuilder.() -> Unit
        ) = inSpans(
            StyleSpan(typeface?.style ?: Typeface.DEFAULT.style),
            builderAction = builderAction
        )
    }
}