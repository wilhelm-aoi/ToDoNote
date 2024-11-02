package com.example.todoapp



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.models.Category

class CategoryManagementActivity : AppCompatActivity() {

    private lateinit var categoryManager: CategoryManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_management)

        categoryManager = CategoryManager(this)
    }

    private fun saveCategory(category: Category) {
        categoryManager.saveCategory(category)
        finish()
    }
}