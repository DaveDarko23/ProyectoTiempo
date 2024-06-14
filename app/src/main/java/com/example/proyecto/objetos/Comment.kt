package com.example.proyecto.objetos

import java.util.Date

data class Comment(
    val id: Int,
    val comentario: String,
    val fecha: Date,
    val hora: Date
)
