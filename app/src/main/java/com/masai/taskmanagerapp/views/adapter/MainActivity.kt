package com.masai.taskmanagerapp.views.adapter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.masai.taskmanagerapp.R
import com.masai.taskmanagerapp.models.local.Task
import com.masai.taskmanagerapp.models.local.TaskRoomDataBase
import com.masai.taskmanagerapp.models.local.TaskappDAO
import com.masai.taskmanagerapp.models.remote.requests.CreateTaskRequest
import com.masai.taskmanagerapp.models.remote.requests.LoginRequestModel
import com.masai.taskmanagerapp.repository.TaskRepo
import com.masai.taskmanagerapp.viewmodels.TaskViewModel
import com.masai.taskmanagerapp.viewmodels.TaskViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), onTaskItemClicked {

    lateinit var taskAdapter: TasksAdapter
    private var tasksList = mutableListOf<Task>()

    val viewModel: TaskViewModel by viewModels()
     lateinit var roomDb: TaskRoomDataBase
     lateinit var taskDao: TaskappDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

//        roomDb = TaskRoomDataBase.getDatabaseObject(this)
//        taskDao = roomDb.getTaskDAO()
//
//        val repo = TaskRepo(taskDao)
//        val viewModelFactory = TaskViewModelFactory(repo)
//        //viewModel = ViewModelProviders.of(this).get(TasksAdapter.TaskViewHolder::class.java)
//        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel::class.java)

        val loginRequestModel = LoginRequestModel(
            userName = "pradeep1706108@gmail.com",
            password = "dhankhar"
        )

//        viewModel.userLogin(loginRequestModel).observe(this, Observer {
//            val response = it
//
//            when(response.status){
//
//                Status.SUCCESS ->{
//                    val name = response.data?.user?.name.toString()
//                    val email = response.data?.user?.email.toString()
//
//                    longToast(name+" and "+email)
//                }
//                Status.ERROR -> {
//                    val error = response.message
//                    longToast("error = $error")
//                }
//                Status.LOADING ->{
//                    longToast("loading data")
//                }
//            }
//        })


        fab.setOnClickListener { view ->

            val createTaskRequest = CreateTaskRequest(
                description = "This is Description",
                status = 0,
                title = "Title"
            )
            viewModel.createNewTask(createTaskRequest)
            viewModel.getTasksFromAPI()
        }


        taskAdapter = TasksAdapter(this, tasksList, this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = taskAdapter

        viewModel.getTasksFromDB().observe(this, {
            tasksList.clear()
            tasksList.addAll(it)
            tasksList.reverse()
            taskAdapter.notifyDataSetChanged()
        })
    }

    override fun onEditClicked(task: Task) {
        val newTitle = "New Title"
        val newDesc = "New Desc"
        task.title = newTitle
        task.desc = newDesc

        viewModel.updateTask(task)
    }

    override fun onDeleteClicked(task: Task) {
        viewModel.deleteTask(task)
    }

}

