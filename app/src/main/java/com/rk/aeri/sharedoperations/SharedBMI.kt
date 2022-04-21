package com.rk.aeri.sharedoperations

import android.content.Context

class SharedBMI(context: Context) {

	private val preference = context.getSharedPreferences ( "BMI" , Context.MODE_PRIVATE )

	fun saveTarget ( measure : String , target: Float ) {

		preference.edit().putFloat( measure , target ).apply()
	}

	fun getTarget ( measure: String ) : Float {

		return preference.getFloat ( measure , 0f )
	}
}