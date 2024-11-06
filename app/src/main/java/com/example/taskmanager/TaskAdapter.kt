
package com.example.taskmanager

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.Listener.SelectionModeListener
import com.example.taskmanager.databinding.ItemTaskBinding
import com.example.taskmanager.db.Task

class TaskAdapter(private val taskClickListener: MainActivity,
                  private val selectionModeListener: SelectionModeListener
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    private val tasks = mutableListOf<Task>()
    private val selectedTasks = mutableSetOf<Long>()
    private val allTasks = mutableListOf<Task>() // 保存所有任务的完整列表
    var isSelectionMode = false
        private set

    interface TaskClickListener {
        fun onTaskClick(task: Task)
    }
    fun hasSelectedTasks(): Boolean {
        return selectedTasks.isNotEmpty()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        allTasks.clear()
        allTasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        tasks.clear()
        if (query.isEmpty()) {
            tasks.addAll(allTasks)
        } else {
            tasks.addAll(allTasks.filter {
                it.title.contains(query, ignoreCase = true) ||
                        (it.description?.contains(query, ignoreCase = true) ?: false)
            })
        }
        notifyDataSetChanged()
    }


    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(task: Task) {
            binding.textViewTaskName.text = task.title
            binding.textViewTaskDescription.text = task.description
            binding.root.setOnClickListener {
                taskClickListener.onTaskClick(task)
            }
            binding.checkBoxSelect.visibility = if (isSelectionMode) View.VISIBLE else View.GONE
            binding.checkBoxSelect.isChecked = selectedTasks.contains(task.id)


            itemView.setOnLongClickListener {
                isSelectionMode = true
                selectionModeListener.onSelectionModeChanged(isSelectionMode)
                notifyDataSetChanged()
                true
            }

            itemView.setOnClickListener {
                if (isSelectionMode) {
                    task.id?.let { id ->
                        toggleSelection(id)
                    }
                } else {
                    taskClickListener.onTaskClick(task)
                }
            }
        }



    private fun toggleSelection(taskId: Long) {
            if (selectedTasks.contains(taskId)) {
                selectedTasks.remove(taskId)
            } else {
                selectedTasks.add(taskId)
            }
            notifyItemChanged(adapterPosition)
        }



    }

    @SuppressLint("NotifyDataSetChanged")
    fun selectAll() {
        selectedTasks.clear()
        tasks.forEach { task ->
            task.id?.let { id ->
                selectedTasks.add(id)
            }
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelection() {
        selectedTasks.clear()
        isSelectionMode = false
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteSelectedTasks() {
        tasks.removeAll { selectedTasks.contains(it.id) }
        selectedTasks.clear()
        isSelectionMode = false
        notifyDataSetChanged()
    }

    fun getSelectedTaskCount(): Int = selectedTasks.size
}


