package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adapters.TaskAdapter
import com.example.proyecto.objetos.CurrentTaskManager
import com.example.proyecto.objetos.CurrentUserManager
import com.example.proyecto.objetos.TaskManager
import com.example.proyecto.objetos.Worker

class WorkerTaskListActivity : AppCompatActivity() {
    private lateinit var taskRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_task_list)

        // Configurar RecyclerView
        taskRecyclerView = findViewById(R.id.taskRecyclerView)

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

            // Configurar el adaptador
            val adapter = TaskAdapter(tasks) { selectedTask ->
                CurrentTaskManager.setCurrentTask(selectedTask)
                val intent = Intent(this, TaskActivity::class.java)
                startActivity(intent)
            }
            taskRecyclerView.layoutManager = LinearLayoutManager(this)
            taskRecyclerView.adapter = adapter

        } else {
            // Mostrar un mensaje de error si el usuario no es un Worker
            Toast.makeText(this, "El usuario actual no es un trabajador", Toast.LENGTH_SHORT).show()
        }
    }
}
