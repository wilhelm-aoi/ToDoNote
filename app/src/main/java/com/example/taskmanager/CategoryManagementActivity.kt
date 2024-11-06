package com.example.taskmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.databinding.ActivityCategoryManagementBinding

class CategoryManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryManagementBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter()
        binding.recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(this@CategoryManagementActivity)
            adapter = categoryAdapter
        }
    }

    private fun setupListeners() {
        binding.fabAddCategory.setOnClickListener {
            // Logic to add a new category
        }
    }
}