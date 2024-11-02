package com.example.todoapp

import com.example.todoapp.models.Task

class SearchFunctionality(private val taskManager: TaskManager) {

    fun searchTasks(query: String): List<Task> {
        // Search tasks by title
        return taskManager.getAllTasks().filter { it.title.contains(query, ignoreCase = true) }
    }
}