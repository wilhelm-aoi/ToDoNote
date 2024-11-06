package com.example.taskmanager.Listener


import androidx.appcompat.widget.SearchView

class TaskSearchQueryListener(
    private val onQuerySubmit: (String) -> Unit,
    private val onQueryChange: (String) -> Unit
) : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            onQuerySubmit(it)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            onQueryChange(it)
        }
        return true
    }
}