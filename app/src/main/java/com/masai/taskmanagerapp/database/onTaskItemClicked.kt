package com.masai.taskmanagerapp.database

import com.masai.taskmanagerapp.models.Task

interface onTaskItemClicked {

    fun onEditClicked(task: Task)

    fun onDeleteClicked(task: Task)
}