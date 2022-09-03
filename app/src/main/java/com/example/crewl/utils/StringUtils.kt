/**
 * @author Kaan Fırat
 *
 * Last updated time : 1 September 2022 14:10
 */

package com.example.crewl.utils

import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.crewl.core.BaseApplication

/* String extensions */

private val applicationContext = BaseApplication.getContext()

fun String.getDrawable(): Drawable? {
    val resourceId = applicationContext.resources.getIdentifier(
        this,
        "drawable",
        applicationContext.packageName
    )

    return AppCompatResources.getDrawable(applicationContext, resourceId)
}

fun String.getDrawableID(): Int =
    applicationContext.resources.getIdentifier(
        this,
        "drawable",
        applicationContext.packageName
    )
