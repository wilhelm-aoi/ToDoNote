package com.example.todoapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.todoapp.models.Task
import com.example.todoapp.models.Category

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Create tables
        db.execSQL(CREATE_TASK_TABLE)
        db.execSQL(CREATE_CATEGORY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Upgrade database schema if necessary
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORY")
        onCreate(db)
    }

    fun getAllTasks(): List<Task> {
        // Retrieve all tasks from the database
        // Implementation goes here
    }

    fun saveTask(task: Task) {
        // Save or update task in the database
        // Implementation goes here
    }

    fun deleteTask(taskId: Long) {
        // Delete task from the database
        // Implementation goes here
    }

    fun getAllCategories(): List<Category> {
        // Retrieve all categories from the database
        // Implementation goes here
    }

    fun saveCategory(category: Category) {
        // Save or update category in the database
        // Implementation goes here
    }

    fun deleteCategory(categoryId: Long) {
        // Delete category from the database
        // Implementation goes here
    }

    companion object {
        private const val DATABASE_NAME = "todoapp.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_TASK = "task"
        private const val TABLE_CATEGORY = "category"

        private const val CREATE_TASK_TABLE = """
            CREATE TABLE $TABLE_TASK (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                description TEXT,
                dueDate INTEGER,
                categoryId INTEGER,
                isCompleted INTEGER NOT NULL,
                createdAt INTEGER NOT NULL
            )
        """

        private const val CREATE_CATEGORY_TABLE = """
            CREATE TABLE $TABLE_CATEGORY (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                color TEXT NOT NULL
            )
        """
    }
}