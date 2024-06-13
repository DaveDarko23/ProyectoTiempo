package com.example.proyecto.objetos

import com.example.proyecto.objetos.User
import com.example.proyecto.objetos.Working

class Worker(
    id: Int,
    username: String,
    name: String,
    password: String,
    userType: String,
    val horaEntrada: String,
    val horaSalida: String,
    val workingList: MutableList<Working> = mutableListOf() // Lista de tareas asignadas al trabajadors
) : User(id, username, name, password, userType)
