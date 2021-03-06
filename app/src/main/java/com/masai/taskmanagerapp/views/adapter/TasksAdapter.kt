package com.masai.taskmanagerapp.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.masai.taskmanagerapp.R
import com.masai.taskmanagerapp.models.local.Task

class TasksAdapter(
    val context: Context,
    val tasksList: MutableList<Task>,
    var listener: onTaskItemClicked
) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(context).inflate(R.layout.task_item_row,parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasksList.get(position)
        holder.title.text = task.title
        holder.desc.text = task.desc

        holder.delete.setOnClickListener {
            listener.onDeleteClicked(task)
        }
        holder.editTv.setOnClickListener {
            listener.onEditClicked(task)
        }
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var desc: TextView
        var editTv: TextView
        var delete: TextView

        init {
            title = itemView.findViewById(R.id.tvTaskTitle)
            desc = itemView.findViewById(R.id.tvDesc)
            editTv = itemView.findViewById(R.id.editTv)
            delete = itemView.findViewById(R.id.deleteTv)
        }
    }
}