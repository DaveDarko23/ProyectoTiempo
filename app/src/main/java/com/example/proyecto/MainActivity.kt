package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.objetos.Administrator
import com.example.proyecto.objetos.CurrentUserManager
import com.example.proyecto.objetos.Task
import com.example.proyecto.objetos.TaskManager
import com.example.proyecto.objetos.UserManager
import com.example.proyecto.objetos.Worker
import com.example.proyecto.objetos.Working

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Comprobar si hay usuarios registrados
        if (UserManager.getUsers().isEmpty()) {
            // Si no hay usuarios, crear un administrador predeterminado
            val defaultAdmin = Administrator(
                0,
                "Admin",
                "Admin",
                "123",
                "Admin",
                "IT",
                "Manage IT infrastructure",
                "Full-time"
            )
            UserManager.addUser(defaultAdmin)
        }
        /*val intent = Intent(this, PruebaActivity::class.java)
        startActivity(intent)*/

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        val worker1 = Worker(
            id = 0,
            username = "DaveDarko",
            name = "Dave Darko",
            password = "123",
            userType = "Worker",
            horaEntrada = "09:00",
            horaSalida = "17:00"
        )

        val worker2 = Worker(
            id = 1,
            username = "Oscaro",
            name = "Oscar O",
            password = "123",
            userType = "Worker",
            horaEntrada = "09:00",
            horaSalida = "17:00"
        )

        // Crear tareas con distintas prioridades
        val tasks = listOf(
            Task(id = 0, title = "Tarea 0", description = "Descripción de la Tarea 0", priority = "Medium"),
            Task(id = 1, title = "Tarea 1", description = "Descripción de la Tarea 1", priority = "Low"),
            Task(id = 2, title = "Tarea 2", description = "Descripción de la Tarea 2", priority = "High"),
            Task(id = 3, title = "Tarea 3", description = "Descripción de la Tarea 3", priority = "Medium"),
            Task(id = 4, title = "Tarea 4", description = "Descripción de la Tarea 4", priority = "Low"),
            Task(id = 5, title = "Tarea 5", description = "Descripción de la Tarea 5", priority = "High")
        )

        // Agregar tareas al TaskManager
        tasks.forEach { task -> TaskManager.addTask(task) }

        // Asignar tareas a los trabajadores
        worker1.workingList.add(Working(idTask = 0))
        worker1.workingList.add(Working(idTask = 1))
        worker1.workingList.add(Working(idTask = 4))
        worker1.workingList.add(Working(idTask = 5))

        worker2.workingList.add(Working(idTask = 2))
        worker2.workingList.add(Working(idTask = 3))
        worker2.workingList.add(Working(idTask = 5))

        // Agregar trabajadores al UserManager
        UserManager.addUser(worker1)
        UserManager.addUser(worker2)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val user = UserManager.findUserByUsername(username)
            if (user != null && user.password == password) {
                CurrentUserManager.setCurrentUser(user)
                if (user.userType.equals("Admin")) {
                    //val intent = Intent(this, AdminMenuActivity::class.java)
                    val intent = Intent(this, TaskListNavigationActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, TaskListNavigationActivity::class.java)
                    startActivity(intent)
                }
            } else {
                // Fallo en la autenticación
                Toast.makeText(
                    this,
                    "Nombre de usuario o contraseña incorrectos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}