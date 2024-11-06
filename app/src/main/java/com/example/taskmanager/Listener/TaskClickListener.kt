package com.example.taskmanager.Listener

import com.example.taskmanager.db.Task

interface TaskClickListener {
    fun onTaskClick(task: Task)
}

