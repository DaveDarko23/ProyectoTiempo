package com.example.proyecto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.objetos.UserManager
import com.example.proyecto.objetos.Worker

class CreateWorkersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_workers)

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val horaEntradaEditText = findViewById<EditText>(R.id.horaEntradaEditText)
        val horaSalidaEditText = findViewById<EditText>(R.id.horaSalidaEditText)
        val createWorkerButton = findViewById<Button>(R.id.createWorkerButton)

        createWorkerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val name = nameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val horaEntrada = horaEntradaEditText.text.toString()
            val horaSalida = horaSalidaEditText.text.toString()

            val id = UserManager.getUsersSize()// Generar ID utilizando el tamaño de la lista de usuarios

            val worker = Worker(id, username, name, password, "Worker", horaEntrada, horaSalida)

            UserManager.addUser(worker)

            Toast.makeText(this, "Trabajador $username creado exitosamente", Toast.LENGTH_SHORT).show()

            // Limpia los campos después de crear el trabajador
            usernameEditText.text.clear()
            nameEditText.text.clear()
            passwordEditText.text.clear()
            horaEntradaEditText.text.clear()
            horaSalidaEditText.text.clear()
        }
    }
}