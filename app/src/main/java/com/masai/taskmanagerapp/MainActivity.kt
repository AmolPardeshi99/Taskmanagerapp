package com.masai.taskmanagerapp

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.masai.taskmanagerapp.adapter.TasksAdapter
import com.masai.taskmanagerapp.database.DatabaseHandler
import com.masai.taskmanagerapp.database.onTaskItemClicked
import com.masai.taskmanagerapp.models.Task
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), onTaskItemClicked {

    lateinit var taskAdapter: TasksAdapter
    private var tasksList = mutableListOf<Task>()
    val dbHandler = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Hello", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            dbHandler.insertTask("Buy Ice-cream", "Buy Fresh")
            updateUI()
        }

//        tasksList = dbHandler.getTaskData()
        tasksList.addAll(dbHandler.getTaskData())

        taskAdapter = TasksAdapter(this, tasksList, this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = taskAdapter
    }

    override fun onEditClicked(task: Task) {
        val newTitle = "New Title"
        val newDesc = "New Desc"
        task.title = newTitle
        task.desc = newDesc

        dbHandler.editTask(task)
        updateUI()
    }

    override fun onDeleteClicked(task: Task) {
        dbHandler.deleteTask(task)
        updateUI()
    }

    fun updateUI() {
        val latestTask = dbHandler.getTaskData()
        tasksList.clear()
        tasksList.addAll(latestTask)
        taskAdapter.notifyDataSetChanged()
    }


}