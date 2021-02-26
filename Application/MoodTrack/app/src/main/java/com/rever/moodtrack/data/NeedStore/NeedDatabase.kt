package com.rever.moodtrack.data.NeedStore

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Need::class], version = 1, exportSchema = false)
abstract class NeedDatabase: RoomDatabase() {

    abstract fun needDao(): NeedDao

    companion object{
        @Volatile
        private  var INSTANCE: NeedDatabase? = null

        fun getDatabase(context: Context):NeedDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        NeedDatabase::class.java,
                        "need_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}