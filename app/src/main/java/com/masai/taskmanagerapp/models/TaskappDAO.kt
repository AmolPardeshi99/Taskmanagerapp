package com.masai.taskmanagerapp.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TaskappDAO {

    // insert data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(task: Task)

    // fetch data @Query("select id,title,`desc` from tasks ")
    @Query("select * from tasks")
    fun getTasks(): LiveData<List<Task>>


}