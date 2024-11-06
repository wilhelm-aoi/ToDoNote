package com.example.taskmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanager.databinding.ActivityTaskEditBinding
import com.example.taskmanager.db.DatabaseHelper
import com.example.taskmanager.db.Task
import android.util.Log

class TaskEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskEditBinding
    private lateinit var dbHelper: DatabaseHelper
    private var taskId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        taskId = intent.getLongExtra("TASK_ID", -1)
        if (taskId != -1L) {
            loadTask(taskId!!)
        }

        binding.btnSaveTask.setOnClickListener {
            if (validateInput()) {
                saveTask()
                Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun loadTask(taskId: Long) {
        val task = dbHelper.getTaskById(taskId)
        task?.let {
            binding.editTextTaskName.setText(it.title)
            binding.editTextTaskDescription.setText(it.description)
        }
    }

    private fun validateInput(): Boolean {
        val taskName = binding.editTextTaskName.text.toString()
        val taskDescription = binding.editTextTaskDescription.text.toString()

        if (taskName.isBlank()) {
            binding.editTextTaskName.error = "Task name is required"
            return false
        }

        if (taskDescription.isBlank()) {
            binding.editTextTaskDescription.error = "Task description is required"
            return false
        }

        return true
    }

    private fun saveTask() {
        val task = Task(
            id = taskId,
            title = binding.editTextTaskName.text.toString(),
            description = binding.editTextTaskDescription.text.toString(),
            dueDate = null,
            categoryId = null,
            isCompleted = false,
            createdAt = System.currentTimeMillis()
        )
        dbHelper.saveTask(task)
    }

    override fun onPause() {
        super.onPause()
        if (validateInput()) {
            saveTask()
        }
    }
}