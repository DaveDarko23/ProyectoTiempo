package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.objetos.CurrentTaskManager
import com.example.proyecto.objetos.CurrentUserManager
import com.example.proyecto.objetos.TaskManager
import com.example.proyecto.objetos.Worker

class WorkerTaskListActivity : AppCompatActivity() {
    private lateinit var taskListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_task_list)

        // Configurar ListView
        taskListView = findViewById(R.id.taskListView)

        // Obtener al usuario actual (asumiendo que está logueado)
        val currentUser = CurrentUserManager.getCurrentUser()

        // Verificar que el usuario sea un Worker
        if (currentUser is Worker) {
            // Obtener la lista de tareas en las que el trabajador está trabajando
            val workingTasks = currentUser.workingList

            // Obtener los IDs de las tareas
            val taskIds = workingTasks.map { it.idTask }

            // Obtener las tareas correspondientes de TaskManager
            val tasks = taskIds.mapNotNull { id -> TaskManager.getTaskById(id) }

            // Obtener los nombres de las tareas
            val taskNames = tasks.map { it.title }

            // Crear un adaptador para el ListView
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, taskNames)
            taskListView.adapter = adapter

            // Manejar clic en los elementos del ListView
            taskListView.setOnItemClickListener { _, _, position, _ ->
                val selectedTask = tasks[position]
                CurrentTaskManager.setCurrentTask(selectedTask)
                val intent = Intent(this, TaskActivity::class.java)
                startActivity(intent)
            }

        } else {
            // Mostrar un mensaje de error si el usuario no es un Worker
            Toast.makeText(this, "El usuario actual no es un trabajador", Toast.LENGTH_SHORT).show()
        }
    }
}