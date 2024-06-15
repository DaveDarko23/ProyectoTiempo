package com.example.proyecto.objetos

import android.webkit.WebSettings.RenderPriority

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: String = "Low",
    var timeSpent: Long = 0, // Tiempo dedicado a la tarea
    val comments: MutableList<Comment> = mutableListOf() // Lista de tareas asignadas al trabajadors
)
