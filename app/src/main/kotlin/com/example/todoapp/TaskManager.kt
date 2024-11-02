package com.example.todoapp

import android.content.Context
import com.example.todoapp.models.Task

class TaskManager(private val context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun getAllTasks(): List<Task> {
        // Retrieve all tasks from the database
        return dbHelper.getAllTasks()
    }

    fun saveTask(task: Task) {
        // Save or update task in the database
        dbHelper.saveTask(task)
    }

    fun deleteTask(taskId: Long) {
        // Delete task from the database
        dbHelper.deleteTask(taskId)
    }
}