package com.masai.taskmanagerapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.masai.taskmanagerapp.models.Task

class DatabaseHandler(val context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        val DB_NAME = "taskdb"
        val DB_VERSION = 1
        val TABLE_NAME = "tasks"
        val ID = "id"
        val TITLE = "title"
        val DESC = "desc"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // called once initially  can run quries

        // initializing table:- making query
        val CREATE_TABLE_QUERY = "CREATE TABLE" +
                " $TABLE_NAME (" +
                "$ID INTEGER PRIMARY KEY, " +
                "$TITLE TEXT, " +
                "$DESC TEXT)"

        // executing query
        db?.execSQL(CREATE_TABLE_QUERY)
    }

    fun insertTask(title: String, desc: String) {
        val db = writableDatabase

        // content values helps to create map
        val values = ContentValues()
        values.put(TITLE, title)
        values.put(DESC, desc)

        val idValue = db.insert(TABLE_NAME, null, values)
        if (idValue.toInt() == -1) {
            Toast.makeText(context, "Failed to insert the data", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Data inserted Successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    fun getTaskData(): MutableList<Task> {
        var taskList = mutableListOf<Task>()
        val db = readableDatabase
        val query = "select * from $TABLE_NAME"

        val cursor = db?.rawQuery(query, null)

        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID))
                val title = cursor.getString(cursor.getColumnIndex(TITLE))
                val desc = cursor.getString(cursor.getColumnIndex(DESC))

                val task = Task()
                task.title = title
                task.desc = desc
                task.id = id

                taskList.add(task)
            } while (cursor.moveToNext())
        }
        return taskList
    }

    fun editTask(task: Task) {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TITLE, task.title)
        contentValues.put(DESC, task.desc)

        val result = db.update(TABLE_NAME, contentValues, "id=${task.id}", null)
        // showing toast on basis of
        if (result != -1) {
            Toast.makeText(context, "Data is updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Data update failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteTask(task: Task) {
        val db = writableDatabase
        val resut = db.delete(TABLE_NAME, "id=${task.id}", null)

        if (resut != -1) {
            Toast.makeText(context, "Data deletion failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Data deleted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // every time we release app this method is invoked
    }

}