package com.masai.taskmanagerapp

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.masai.taskmanagerapp.adapter.TasksAdapter
import com.masai.taskmanagerapp.database.onTaskItemClicked
import com.masai.taskmanagerapp.models.Task
import com.masai.taskmanagerapp.models.TaskRoomDataBase
import com.masai.taskmanagerapp.models.TaskappDAO
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), onTaskItemClicked {

    lateinit var taskAdapter: TasksAdapter
    private var tasksList = mutableListOf<Task>()

    lateinit var roomDb: TaskRoomDataBase
    lateinit var taskDao: TaskappDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        roomDb = TaskRoomDataBase.getDatabaseObject(this)
        taskDao = roomDb.getTaskDAO()

        fab.setOnClickListener { view ->
            val newtask = Task("Dummy Title2", "Dummy Desc2")

            CoroutineScope(Dispatchers.IO).launch {
                taskDao.addTask(newtask)
            }
        }

        taskAdapter = TasksAdapter(this, tasksList, this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = taskAdapter

            taskDao.getTasks().observe(this@MainActivity, Observer {
                val tasks = it
                tasksList.clear()
                tasksList.addAll(tasks)
               taskAdapter.notifyDataSetChanged()
            })
    }

    override fun onEditClicked(task: Task) {
        val newTitle = "New Title"
        val newDesc = "New Desc"
        task.title = newTitle
        task.desc = newDesc

    }

    override fun onDeleteClicked(task: Task) {
    }

}


//   fun updateUI() {
//        val latestTask = dbHandler.getTaskData()
//        tasksList.clear()
//        tasksList.addAll(latestTask)
//        taskAdapter.notifyDataSetChanged()

//            Snackbar.make(view, "Hello", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()