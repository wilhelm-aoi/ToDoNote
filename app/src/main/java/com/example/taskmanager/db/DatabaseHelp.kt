package com.example.taskmanager.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    @Override
    override fun onCreate(db: SQLiteDatabase) {
        // Create tables
        db.execSQL(CREATE_TASK_TABLE)
        db.execSQL(CREATE_CATEGORY_TABLE)
    }
    @Override
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Upgrade database schema if necessary
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORY")
        onCreate(db)
    }

    fun searchTasks(query: String): List<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_TASK,
            null,
            "title LIKE ? OR description LIKE ?",
            arrayOf("%$query%", "%$query%"),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    dueDate = cursor.getLong(cursor.getColumnIndexOrThrow("dueDate")),
                    categoryId = cursor.getLong(cursor.getColumnIndexOrThrow("categoryId")),
                    isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow("isCompleted")) == 1,
                    createdAt = cursor.getLong(cursor.getColumnIndexOrThrow("createdAt"))
                )
                tasks.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tasks
    }

    fun getAllTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_TASK, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    dueDate = cursor.getLong(cursor.getColumnIndexOrThrow("dueDate")),
                    categoryId = cursor.getLong(cursor.getColumnIndexOrThrow("categoryId")),
                    isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow("isCompleted")) == 1,
                    createdAt = cursor.getLong(cursor.getColumnIndexOrThrow("createdAt"))
                )
                tasks.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tasks
    }

    fun saveTask(task: Task) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", task.title)
            put("description", task.description)
            put("dueDate", task.dueDate)
            put("categoryId", task.categoryId)
            put("isCompleted", if (task.isCompleted) 1 else 0)
            put("createdAt", task.createdAt)
        }

        if (task.id == null || task.id == -1L) {
            // Insert new task
            val newRowId = db.insert(TABLE_TASK, null, values)
            if (newRowId == -1L) {
                Log.e("DatabaseHelper", "Error inserting new task: $task")
            }
        } else {
            // Update existing task
            val count = db.update(
                TABLE_TASK,
                values,
                "id = ?",
                arrayOf(task.id.toString())
            )
            if (count == 0) {
                Log.e("DatabaseHelper", "Error updating task: $task")
            }
        }
    }

    fun deleteTask(taskId: Long?) {
        val db = writableDatabase
        db.delete(TABLE_TASK, "id = ?", arrayOf(taskId.toString()))
    }

    fun getAllCategories(): List<Category> {
        val categories = mutableListOf<Category>()
        val db = readableDatabase
        val cursor: Cursor = db.query(TABLE_CATEGORY, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val category = Category(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    color = cursor.getString(cursor.getColumnIndexOrThrow("color"))
                )
                categories.add(category)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return categories
    }

    fun saveCategory(category: Category) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", category.name)
            put("color", category.color)
        }

        if (category.id == null) {
            db.insert(TABLE_CATEGORY, null, values)
        } else {
            db.update(TABLE_CATEGORY, values, "id = ?", arrayOf(category.id.toString()))
        }
    }

    fun deleteCategory(categoryId: Long) {
        val db = writableDatabase
        db.delete(TABLE_CATEGORY, "id = ?", arrayOf(categoryId.toString()))
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

    fun getTaskById(taskId: Long): Task? {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_TASK,
            null,
            "id = ?",
            arrayOf(taskId.toString()),
            null,
            null,
            null
        )

        var task: Task? = null
        if (cursor.moveToFirst()) {
            task = Task(
                id = cursor.getLong(cursor.getColumnIndexOrThrow("id")),
                title = cursor.getString(cursor.getColumnIndexOrThrow("title")),
                description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                dueDate = cursor.getLong(cursor.getColumnIndexOrThrow("dueDate")),
                categoryId = cursor.getLong(cursor.getColumnIndexOrThrow("categoryId")),
                isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow("isCompleted")) == 1,
                createdAt = cursor.getLong(cursor.getColumnIndexOrThrow("createdAt"))
            )
        }
        cursor.close()
        return task
    }
}

