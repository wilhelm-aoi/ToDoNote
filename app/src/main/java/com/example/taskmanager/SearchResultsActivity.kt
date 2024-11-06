package com.example.taskmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.Listener.SelectionModeListener
import com.example.taskmanager.Listener.TaskClickListener
import com.example.taskmanager.databinding.ActivitySearchResultsBinding
import com.example.taskmanager.db.DatabaseHelper
import com.example.taskmanager.db.Task

class SearchResultsActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySearchResultsBinding
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)


        // 获取传递过来的搜索关键字
        val query = intent.getStringExtra("SEARCH_QUERY")
        query?.let {
            searchTasks(it)
        }
    }



    private fun searchTasks(query: String) {
        val searchResults = dbHelper.getAllTasks().filter {
            it.title.contains(query, ignoreCase = true) ||
                    (it.description?.contains(query, ignoreCase = true) ?: false)
        }
        taskAdapter.setTasks(searchResults)
    }
}