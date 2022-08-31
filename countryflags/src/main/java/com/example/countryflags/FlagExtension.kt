/**
 * @author Kaan Fırat
 *
 * Last updated time : 25 August 2022 15:03
 */

package com.example.countryflags

import android.content.Context
import java.util.*

fun Context.getCountryFlagWithResID(code: String): Int {
    val countryLetter: String
    var name = "flag"

    if (code.length == 2) {
        countryLetter = code.uppercase(Locale.getDefault())

        countryLetter.forEach { char ->
            name += "_${Integer.toHexString(char.code - 0x41 + 0x1F1E6)}"
        }
    } else {
        countryLetter = code.replace("-", "").toLowerCase(Locale.getDefault())
        name += "_${Integer.toHexString(0x1F3F4)}"

        countryLetter.forEach { char ->
            name += "_${Integer.toHexString(char.code - 0x61 + 0xE0061)}"
        }

        name += "_${Integer.toHexString(0xE007F)}"
    }

    return this.resources.getIdentifier(name, "drawable", this.packageName)
}