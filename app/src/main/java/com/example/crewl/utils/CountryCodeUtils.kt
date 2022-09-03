/**
 * @author Kaan Fırat
 *
 * Last updated time : 3 September 2022 05:34
 */

package com.example.crewl.utils

class CountryCodeUtils {
    companion object {
        @JvmStatic
        fun isoCodeToEmoji(countryIsoCode: String): String {
            val firstLetter = Character.codePointAt(countryIsoCode, 0) - 0x41 + 0x1F1E6
            val secondLetter = Character.codePointAt(countryIsoCode, 1) - 0x41 + 0x1F1E6

            return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
        }
    }
}