package com.rk.aeri.dboperations.repository

import androidx.lifecycle.LiveData
import com.rk.aeri.dboperations.commands.StepDao
import com.rk.aeri.dboperations.model.Step

class StepRepository ( private val stepDao: StepDao) {

    suspend fun addRow (step: Step){

        stepDao.addRow(step)
    }

    fun weekRead ( day : Int , month: Int ) : LiveData<List<Step>> {

        return stepDao.weekRead ( day , month )
    }

    fun monthRead ( month : Int ) : LiveData<List<Step>> {

        return stepDao.monthRead ( month )
    }
}