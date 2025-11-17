package com.ubayadev.todoapp.view

import com.ubayadev.todoapp.model.Todo

interface TodoItemListener {
    fun onTodoCheck(todo: Todo)
}