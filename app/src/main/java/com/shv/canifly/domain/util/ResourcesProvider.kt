package com.shv.canifly.domain.util

import android.content.Context
import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

    fun getColor(@ColorRes colorResId: Int): Int {
        return context.getColor(colorResId)
    }

    fun getColorWithAlpha(@ColorRes colorResId: Int): Int {
        val color = getColor(colorResId)
        val alpha = 128
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        return Color.argb(alpha, red, green, blue)
    }

}