/**
 * @author Kaan Fırat
 *
 * Last updated time : 3 September 2022 05:33
 */

package com.example.crewl.utils

import android.graphics.Typeface
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.core.text.inSpans
import androidx.fragment.app.Fragment

val Editable.asString: String
    get() = this.toString()

fun SpannableStringBuilder.font(typeface: Typeface? = null, builderAction: SpannableStringBuilder.() -> Unit) =
    inSpans(StyleSpan(typeface?.style ?: Typeface.DEFAULT.style), builderAction = builderAction)

/**
 * Creates an [AutoClearedValue] associated with this fragment.
 */
fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)