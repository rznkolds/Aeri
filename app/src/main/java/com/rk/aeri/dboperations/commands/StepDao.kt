package com.rk.aeri.dboperations.commands

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rk.aeri.dboperations.model.Step

@Dao
interface StepDao {

    @Insert ( onConflict = OnConflictStrategy.REPLACE )
    suspend fun addRow ( step: Step)

    @Query ( "SELECT * FROM step_table WHERE day >=:day AND month=:month ORDER BY day DESC" )
    fun weekRead ( day : Int , month: Int ) : LiveData<List<Step>>

    @Query ( "SELECT * FROM step_table WHERE month =:month ORDER BY day DESC" )
    fun monthRead ( month : Int ) : LiveData<List<Step>>
}