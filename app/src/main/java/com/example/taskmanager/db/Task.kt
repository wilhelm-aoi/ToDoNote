package com.example.taskmanager.db

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id: Long? = null,
    var title: String,
    var description: String?,
    var dueDate: Long?,
    var categoryId: Long?,
    var isCompleted: Boolean,
    var createdAt: Long
): Parcelable