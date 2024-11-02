package com.example.todoapp.models

data class Task(
    val id: Long,
    var title: String,
    var description: String?,
    var dueDate: Long?,
    var categoryId: Long?,
    var isCompleted: Boolean,
    var createdAt: Long
)