package com.ubayadev.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ubayadev.todoapp.model.Todo
import com.ubayadev.todoapp.model.TodoDatabase
import com.ubayadev.todoapp.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel(application: Application): AndroidViewModel(application),
                CoroutineScope {
    private val job = Job()

    fun addTodo(todo: Todo) {
        launch {
            val db = buildDB(getApplication())
            db.todoDao().insertAll(todo)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}