package com.masai.taskmanagerapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.masai.taskmanagerapp.models.local.Task
import com.masai.taskmanagerapp.models.remote.network.api.Resource
import com.masai.taskmanagerapp.models.remote.requests.CreateTaskRequest
import com.masai.taskmanagerapp.models.remote.requests.LoginRequestModel
import com.masai.taskmanagerapp.models.remote.responses.LoginResponse
import com.masai.taskmanagerapp.repository.TaskRepo
import kotlinx.coroutines.Dispatchers

class TaskViewModel(val repo: TaskRepo): ViewModel() {

    fun userLogin(loginRequestModel: LoginRequestModel) : LiveData<Resource<LoginResponse>>{

        // running coroutine as  well as creating live data
        // ---> combo of coroutine and livedata
        return liveData(Dispatchers.IO) {
             val result = repo.login(loginRequestModel)
            emit(result) // sending or emitting data to all observer
            // mutablelivedata.value= result
        }
    }


    fun createNewTask(createTaskRequest: CreateTaskRequest){
        repo.createTask(createTaskRequest)
    }

    fun getTasksFromAPI(){
        repo.getRemoteTask()
    }

    fun addTask(task: Task){
        repo.addTaskToRoom(task)
    }

    fun getTasksFromDB(): LiveData<List<Task>> {
        return repo.getAllTask()
    }

    fun updateTask(task: Task){
        repo.updateTask(task)
    }
    fun deleteTask(task: Task){
        repo.deleteTask(task)
    }

}