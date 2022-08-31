package com.example.crewl.helpers

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.example.crewl.core.BaseApplication

object ResourceHelper {
    fun getColor(resourceId: Int): Int {
        val applicationContext = BaseApplication.getContext()
        return ResourcesCompat.getColor(applicationContext.resources, resourceId, null)
    }

    fun getFont(resourceId: Int): Typeface? {
        val applicationContext = BaseApplication.getContext()
        return ResourcesCompat.getFont(applicationContext, resourceId)
    }

    fun getDrawable(resourceId: Int): Drawable? {
        val applicationContext = BaseApplication.getContext()
        return ResourcesCompat.getDrawable(applicationContext.resources, resourceId, null)
    }

    fun getString(resourceId: Int): String =
        BaseApplication.getContext().getString(resourceId)
}