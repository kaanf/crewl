/**
 * @author Kaan Fırat
 *
 * Last updated time : 25 August 2022 16:33
 */

package com.example.crewl.data.model

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import com.example.crewl.utils.CountryCodeUtils.Companion.isoCodeToEmoji

data class Country(
    /**
     * 2 letter country ISO code in upper case. E.g. LV, US, AU.
     */
    val isoCode: String,

    /**
     * International country phone code prefix without `+`, e.g. 1 for US, 371 for LV.
     */
    val phoneCode: String,

    /**
     * Full country name
     */
    val countryName: String
) {

    /**
     * Formatted string that can be displayed in UI.
     * Example `🇱🇻 *+371* Latvia`
     */
    val combinedName: CharSequence

    /**
     * 2 unicode glyphs that correspond to ISO code and in most
     * cases are displayed as country flag emoji, e.g. 🇱🇻.
     * Flag emoji support unfortunately is dependant on device/version and some flags
     * may not be available on some devices.
     */
    private val flagEmoji: String = if (isoCode.isNotBlank()) {
        isoCodeToEmoji(isoCode)
    } else ""

    init {

        val fullName = "$flagEmoji +$phoneCode $countryName"

        val str = SpannableString(fullName)

        str.setSpan(
            StyleSpan(Typeface.BOLD),
            flagEmoji.length + 1,
            flagEmoji.length + phoneCode.length + 2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        combinedName = str
    }

    override fun toString() = combinedName.toString()

    companion object {
        val UNDEFINED = Country("", "", "")
    }
}