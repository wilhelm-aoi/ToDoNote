package com.example.taskmanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.databinding.ActivityMainBinding
import com.example.taskmanager.db.DatabaseHelper
import com.example.taskmanager.db.Task
import com.example.taskmanager.Listener.SelectionModeListener
import com.example.taskmanager.Listener.TaskClickListener

import com.example.taskmanager.Listener.TaskSearchQueryListener

class MainActivity : AppCompatActivity(), TaskClickListener, SelectionModeListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var dbHelper: DatabaseHelper
    private var isSelectionMode: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        setupRecyclerView()
        setupListeners()
        setupSwipeRefresh()
        loadTasks()

        setupSearchView() // Add this line
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadTasks()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }



    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(
            TaskSearchQueryListener(
                onQuerySubmit = { query ->
                    val searchResults = dbHelper.getAllTasks().filter {
                        it.title.contains(query, ignoreCase = true) ||
                                (it.description?.contains(query, ignoreCase = true) ?: false)
                    }
                    taskAdapter.setTasks(searchResults)
                },
                onQueryChange = { newText ->
                    // 实时搜索逻辑
                }
            )
        )
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
        updateSelectionModeUI()
    }
    override fun onSelectionModeChanged(isSelectionMode: Boolean) {
        this.isSelectionMode = isSelectionMode
        updateSelectionModeUI()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(this,this) // Pass 'this' as both TaskClickListener and SelectionModeListener
        binding.recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }
    }
    override fun onTaskClick(task: Task) {
        val intent = Intent(this, TaskEditActivity::class.java)
        intent.putExtra("TASK_ID", task.id)
        startActivity(intent)
    }

//    private fun setupRecyclerView() {
//        taskAdapter = TaskAdapter()
//        binding.recyclerViewTasks.apply {
//            layoutManager = LinearLayoutManager(this@MainActivity)
//            adapter = taskAdapter
//        }
//    }

    private fun updateSelectionModeUI() {
        if (isSelectionMode) {
            binding.checkBoxSelectAll.visibility = View.VISIBLE
            binding.btnDeleteSelected.visibility = View.VISIBLE
        } else {
            binding.checkBoxSelectAll.visibility = View.GONE
            binding.btnDeleteSelected.visibility = View.GONE
        }
    }


    private fun setupListeners() {
        binding.fabAddTask.setOnClickListener {
            startActivity(Intent(this, TaskEditActivity::class.java))
        }

        binding.btnCategoryFilter.setOnClickListener {
            startActivity(Intent(this, CategoryManagementActivity::class.java))
        }

        binding.checkBoxSelectAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                taskAdapter.selectAll()
            } else {
                taskAdapter.clearSelection()
            }
            updateSelectionModeUI()
        }

        binding.btnDeleteSelected.setOnClickListener {
            taskAdapter.deleteSelectedTasks()
            updateSelectionModeUI()
        }
    }
//    private fun onTaskClick(task: Task) {
//        val intent = Intent(this, TaskEditActivity::class.java)
//        intent.putExtra("TASK_ID", task.id)
//        startActivity(intent)
//    }

    private fun loadTasks() {
        val tasks = dbHelper.getAllTasks()
        taskAdapter.setTasks(tasks)
    }
}