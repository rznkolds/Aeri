package com.rk.aeri.sharedoperations

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate.*

class SharedTheme ( context: Context ) {

    val prefences = context.getSharedPreferences("ThemeZone", Context.MODE_PRIVATE)

    fun selectedTheme ( name: String ) {

        val editor = prefences.edit()

        editor.putString("theme_value", name).apply()
    }

    fun rememberedTheme ( ) {

        val theme = prefences.getString("theme_value", null)

        when (theme) {

            "light_mode" -> {
                setDefaultNightMode(MODE_NIGHT_NO)
            }

            "dark_mode" -> {
                setDefaultNightMode(MODE_NIGHT_YES)
            }

            else -> {
                setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

}