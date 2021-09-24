package com.masai.taskmanagerapp.repository

import androidx.lifecycle.LiveData
import com.masai.taskmanagerapp.models.local.Task
import com.masai.taskmanagerapp.models.local.TaskappDAO
import com.masai.taskmanagerapp.models.remote.network.api.Network
import com.masai.taskmanagerapp.models.remote.network.api.Resource
import com.masai.taskmanagerapp.models.remote.network.api.ResponseHandler
import com.masai.taskmanagerapp.models.remote.network.api.TasksAPI
import com.masai.taskmanagerapp.models.remote.requests.CreateTaskRequest
import com.masai.taskmanagerapp.models.remote.requests.LoginRequestModel
import com.masai.taskmanagerapp.models.remote.responses.GetTaskResponseModel
import com.masai.taskmanagerapp.models.remote.responses.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class TaskRepo(private val taskDAO: TaskappDAO) {


    private val api: TasksAPI = Network.getRetrofit().create(
        TasksAPI::class.java)
    private val responseHandler = ResponseHandler()
    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MGE0YmI3OTAzMjdlN2MwNmE2MTk1ODYiLCJpYXQiOjE2MzIxMzg2ODR9.cTxpYQrTfvramIOSPih6b1hJO_x1G-V2GmaRnTYSjU0"

    suspend fun login(loginRequestModel: LoginRequestModel): Resource<LoginResponse> {

        return try {
            val response = api.login(loginRequestModel)
            return responseHandler.handleSuccess(response)

        }catch (e: Exception){
            return responseHandler.handleException(e)
        }

    }


    fun getRemoteTask(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.getTaskFromAPI(token)
            saveToDB(response)
        }
    }

    fun createTask(createTaskRequest: CreateTaskRequest){
        CoroutineScope(Dispatchers.IO).launch {
            val response = api.createTask(token,createTaskRequest)
            val newtask = Task(response.title,response.description)
            taskDAO.addTask(newtask)
        }

    }

    private fun saveToDB(response: GetTaskResponseModel) {

        val listOFTask = ArrayList<Task>()
        response.forEach {
            val newTask = Task(it.title,it.description)
            listOFTask.add(newTask)
        }
        taskDAO.deleteAll()
        taskDAO.addTasks(listOFTask)
    }

    fun addTaskToRoom(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDAO.addTask(task)
        }
    }

    fun getAllTask(): LiveData<List<Task>> {
        return taskDAO.getTasks()
    }

    fun updateTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDAO.updateTask(task)
        }
    }

    fun deleteTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            taskDAO.delete(task)
        }
    }
}