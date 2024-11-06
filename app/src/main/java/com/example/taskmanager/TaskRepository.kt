package com.example.taskmanager

import com.example.taskmanager.db.Task

class TaskRepository {

    private val tasks = mutableListOf<Task>()

    fun getTasks(): List<Task> {
        return tasks
    }

    fun addTask(task: Task) {
        tasks.add(task)
    }
}