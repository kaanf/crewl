package com.example.crewl.utils

import android.content.Context
import android.telephony.TelephonyManager
import androidx.annotation.StyleRes
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.example.crewl.ui.bottomSheet.countryCode.CountryCodeFragment
import java.util.*

class CountryCodeUtils {
    companion object {
        @JvmStatic
        fun isoCodeToEmoji(countryIsoCode: String): String {
            val firstLetter = Character.codePointAt(countryIsoCode, 0) - 0x41 + 0x1F1E6
            val secondLetter = Character.codePointAt(countryIsoCode, 1) - 0x41 + 0x1F1E6

            return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
        }

        @JvmStatic
        fun simCountryIsoCode(appContext: Context): String? {
            val tm = appContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val simCountry = tm.simCountryIso

            return if (simCountry?.length == 2) {
                simCountry.uppercase(Locale.US)
            } else if (tm.phoneType != TelephonyManager.PHONE_TYPE_CDMA) {
                val networkCountry = tm.networkCountryIso
                if (networkCountry?.length == 2) {
                    networkCountry.uppercase(Locale.US)
                } else null
            } else null
        }
    }
}