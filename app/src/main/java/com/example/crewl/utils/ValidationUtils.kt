/**
 * @author Kaan Fırat
 * Last updated time : 3 September 2022 12:55
 */

package com.example.crewl.utils

object ValidationUtils {
    fun isPhoneNumberValid(count: Int): Boolean = count == 10

    fun isMailValid(mail: String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()

    fun isPasswordValid(password: String): Boolean = password.isNotEmpty() && password.length > 5
}