package com.example.proyecto.objetos

class Administrator(
    id: Int,
    username: String,
    name: String,
    password: String,
    userType: String,
    val departamento: String,
    val responsabilidades: String,
    val tipoContrato: String
) : User(id, username, name, password, userType)
