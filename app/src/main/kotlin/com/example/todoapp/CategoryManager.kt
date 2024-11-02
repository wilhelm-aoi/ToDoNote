package com.example.todoapp

import android.content.Context
import com.example.todoapp.models.Category

class CategoryManager(private val context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun getAllCategories(): List<Category> {
        // Retrieve all categories from the database
        return dbHelper.getAllCategories()
    }

    fun saveCategory(category: Category) {
        // Save or update category in the database
        dbHelper.saveCategory(category)
    }

    fun deleteCategory(categoryId: Long) {
        // Delete category from the database
        dbHelper.deleteCategory(categoryId)
    }
}