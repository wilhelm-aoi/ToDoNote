package com.example.taskmanager

object ValidationUtils {

    fun isValidTaskName(name: String): Boolean {
        return name.isNotBlank()
    }

    fun isValidTaskDescription(description: String): Boolean {
        return description.isNotBlank()
    }
}