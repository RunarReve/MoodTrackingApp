package com.rever.moodtrack.data.LastStep

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LastStep::class], version = 1, exportSchema = false)
abstract class StepDatabase: RoomDatabase() {

    abstract fun stepDao(): StepDao

    companion object{
        @Volatile
        private  var INSTANCE: StepDatabase? = null

        fun getDatabase(context: Context):StepDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        StepDatabase::class.java,
                        "step_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}