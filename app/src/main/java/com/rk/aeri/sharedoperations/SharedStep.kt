package com.rk.aeri.sharedoperations

import com.rk.aeri.dboperations.repository.StepRepository
import com.rk.aeri.dboperations.apiclient.StepDatabase
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import com.rk.aeri.dboperations.model.Step
import android.content.Context
import kotlinx.coroutines.*

class SharedStep( context: Context) {

    private val preference = context.getSharedPreferences("StepZone", Context.MODE_PRIVATE)

    fun saveStep( context: Context , restart: Boolean, day: Int, month: Int, year: Int, step: Int? ) {

        val edit = preference.edit()

        if (restart) {

            CoroutineScope(CoroutineName("restart-scope") + Dispatchers.IO).launch {

                var tempD = 0
                var tempM = 0
                var tempY = 0
                var tempS = 0

                when (month) {

                    1 -> if (day == 1) {

                        tempD = 31
                        tempM = 12
                        tempY = year - 1
                        tempS = preference.getInt("${31}.${12}.${year - 1}", 0)

                        edit.remove("${31}.${12}.${year - 1}")

                    } else {

                        tempD = day - 1
                        tempM = month
                        tempY = year
                        tempS = preference.getInt("${day - 1}.${month}.${year}", 0)

                        edit.remove("${day - 1}.${month}.${year}")
                    }

                    3 -> if (day == 1) {

                        tempD = 28
                        tempM = month - 1
                        tempY = year
                        tempS = preference.getInt("${28}.${month - 1}.${year}", 0)

                        edit.remove("${28}.${month - 1}.${year}")

                    } else {

                        tempD = day - 1
                        tempM = month
                        tempY = year
                        tempS = preference.getInt("${day - 1}.${month}.${year}", 0)

                        edit.remove("${day - 1}.${month}.${year}")
                    }

                    2, 4, 6, 8, 9, 11 -> if (day == 1) {

                        tempD = 31
                        tempM = month - 1
                        tempY = year
                        tempS = preference.getInt("${31}.${month - 1}.${year}", 0)

                        edit.remove("${31}.${month - 1}.${year}")

                    } else {

                        tempD = day - 1
                        tempM = month
                        tempY = year
                        tempS = preference.getInt("${day - 1}.${month}.${year}", 0)

                        edit.remove("${day - 1}.${month}.${year}")
                    }

                    5, 7, 10, 12 -> if (day == 1) {

                        tempD = 30
                        tempM = month - 1
                        tempY = year
                        tempS = preference.getInt("${31}.${month - 1}.${year}", 0)

                        edit.remove("${31}.${month - 1}.${year}")

                    } else {

                        tempD = day - 1
                        tempM = month
                        tempY = year
                        tempS = preference.getInt("${day - 1}.${month}.${year}", 0)

                        edit.remove("${day - 1}.${month}.${year}")
                    }
                }

                val row = Step ( tempD , tempM , tempY , tempS )

                val repository = StepRepository(StepDatabase.getInstance(context).stepDao())

                repository.addRow(row)
            }

        } else {

            if (step != null) {

                edit.putInt("${day}.${month}.${year}" , step )

                edit.apply()
            }
        }
    }

    fun getStep( day: Int, month: Int, year: Int ): Int {

        return preference.getInt("${day}.${month}.${year}", 0)
    }
}