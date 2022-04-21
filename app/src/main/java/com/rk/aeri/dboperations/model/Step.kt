package com.rk.aeri.dboperations.model

import androidx.room.Entity

@Entity ( tableName = "step_table" , primaryKeys = arrayOf ( "day" , "month" ) )
data class Step (

    val day : Int ,

    val month : Int ,

    val year : Int ,

    val step_value : Int
)