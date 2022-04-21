package com.rk.aeri.dboperations.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rk.aeri.dboperations.apiclient.StepDatabase
import com.rk.aeri.dboperations.model.Step
import com.rk.aeri.dboperations.repository.StepRepository

class StepViewModel (application: Application):AndroidViewModel(application) {

    private val repository : StepRepository

    init {

        val stepDao = StepDatabase.getInstance(application).stepDao()

        repository = StepRepository ( stepDao )
    }

    fun weekRead ( day : Int , month : Int ) : LiveData<List<Step>> {

        return repository.weekRead ( day , month )
    }

    fun monthRead ( month : Int ) : LiveData<List<Step>> {

        return repository.monthRead ( month )
    }
}