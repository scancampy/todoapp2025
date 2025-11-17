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

class ListTodoViewModel(application: Application): AndroidViewModel(application),
                                                    CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        loadingLD.value = true
        errorLD.value = false
        launch {
            val db = buildDB(getApplication())
            todoLD.postValue(db.todoDao().selectAll())
            loadingLD.postValue(false)
        }
    }

    fun clearTask(todo:Todo) {
        launch {
            val db = buildDB(getApplication())
            db.todoDao().deleteTodo(todo)
            todoLD.postValue(db.todoDao().selectAll())
        }
    }

}