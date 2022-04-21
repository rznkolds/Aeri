package com.rk.aeri.sharedoperations

import android.content.Context

class SharedGoal (context : Context ) {

    private val preference = context.getSharedPreferences("StepGoal", Context.MODE_PRIVATE)

    fun saveTarget ( target: Int) {

        preference.edit().putInt ( "progress_goal" , target ).apply ( )
    }

    fun getTarget ( ): Int {

        return preference.getInt("progress_goal", 0)
    }
}