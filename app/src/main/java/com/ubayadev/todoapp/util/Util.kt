package com.ubayadev.todoapp.util

import android.content.Context
import com.ubayadev.todoapp.model.TodoDatabase

val DB_NAME = "tododb"
fun buildDB(context: Context): TodoDatabase {
    val db = TodoDatabase.buildDatabase(context)
    return db
}