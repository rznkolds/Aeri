package com.rk.aeri.dboperations.apiclient

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rk.aeri.dboperations.commands.StepDao
import com.rk.aeri.dboperations.model.Step

@Database(entities = [Step::class] , version = 2 )
abstract class StepDatabase : RoomDatabase() {

    abstract fun stepDao () : StepDao

    companion object {

        @Volatile
        private var INSTANCE : StepDatabase? = null

        fun getInstance ( context: Context ) : StepDatabase {

            val tempInstance = INSTANCE

            if ( tempInstance != null ){

                return tempInstance
            }
            synchronized(this){

                val instance = Room.databaseBuilder(context.applicationContext, StepDatabase::class.java,"step_database").fallbackToDestructiveMigration().build()

                INSTANCE = instance

                return instance
            }
        }
    }
}