package com.ubayadev.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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
    val todoLD = MutableLiveData<Todo>()

    fun addTodo(todo: Todo) {
        launch {
            val db = buildDB(getApplication())
            db.todoDao().insertAll(todo)
        }
    }

    fun fetch(uuid:Int) {
        launch {
            val db = buildDB(getApplication())
            todoLD.postValue(db.todoDao().selectTodo(uuid))
        }
    }

    fun update(todo: Todo) {
        launch {
            val db = buildDB(getApplication())
            db.todoDao().updateTodo(todo)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}