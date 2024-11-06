package com.example.taskmanager

import com.example.taskmanager.db.Category

class CategoryRepository {

    private val categories = mutableListOf<Category>()

    fun getCategories(): List<Category> {
        return categories
    }

    fun addCategory(category: Category) {
        categories.add(category)
    }
}