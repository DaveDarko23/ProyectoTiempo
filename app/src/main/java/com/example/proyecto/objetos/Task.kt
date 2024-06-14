package com.example.proyecto.objetos

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    var timeSpent: Long = 0, // Tiempo dedicado a la tarea
    val comments: MutableList<Comment> = mutableListOf() // Lista de tareas asignadas al trabajadors
)
