package com.rk.aeri.sharedoperations

import android.content.Context

class SharedToast ( context: Context ) {

    private val preference = context.getSharedPreferences("SharedToast", Context.MODE_PRIVATE)

    fun saveTarget ( target : Int ) {

        preference.edit().putInt ( "check_toast" , target ).apply ( )
    }

    fun getTarget ( ) : Int {

        return preference.getInt ( "check_toast" , 0)
    }
}