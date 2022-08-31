package com.example.crewl.utils

import android.graphics.drawable.Drawable
import android.text.Editable
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
