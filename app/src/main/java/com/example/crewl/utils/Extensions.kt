/**
 * @author Kaan Fırat
 *
 * Last updated time : 3 September 2022 05:33
 */

package com.example.crewl.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
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

fun View.withObjectAnimation(property: String, value: Float) {
    ObjectAnimator.ofFloat(this, property, value).start()
}

inline infix fun <T>Boolean.then(action : () -> T): T? {
    return if (this)
        action.invoke()
    else null
}

inline infix fun <T>T?.letElse(action: () -> T): T {
    return this ?: action.invoke()
}

/**
 * Extension method to show a keyboard for View.
 */
fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) { }
    return false
}