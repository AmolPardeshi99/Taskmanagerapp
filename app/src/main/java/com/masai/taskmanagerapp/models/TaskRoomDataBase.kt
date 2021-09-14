package com.masai.taskmanagerapp.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Task::class],version = 1)
abstract class TaskRoomDataBase : RoomDatabase() {

    abstract fun getTaskDAO(): TaskappDAO

    companion object{

        private var INSTANCE : TaskRoomDataBase? = null

        fun getDatabaseObject(context: Context) : TaskRoomDataBase {
            if (INSTANCE ==null){
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    TaskRoomDataBase::class.java,
                    "task_db"
                )

                // builder.fallbackToDestructiveMigration()  - like upgradation method
                INSTANCE = builder.build()
                return INSTANCE as TaskRoomDataBase
                //return INSTANCE!!
            }else return INSTANCE as TaskRoomDataBase
        }
    }
}