package com.example.proyecto

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.objetos.Administrator
import com.example.proyecto.objetos.UserManager
import com.example.proyecto.objetos.Worker

class ShowUsersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_users)

        val usersTextView = findViewById<TextView>(R.id.usersTextView)
        val users = UserManager.getUsers()

        usersTextView.text = users.joinToString(separator = "\n") { user ->
            when (user) {
                is Administrator -> "Admin: ${user.username}, ID: ${user.id}, Departamento: ${user.departamento}"
                is Worker -> "Worker: ${user.username}, ID: ${user.id}, Hora Entrada: ${user.horaEntrada}"
                else -> "User: ${user.username}"
            }
        }
    }
}