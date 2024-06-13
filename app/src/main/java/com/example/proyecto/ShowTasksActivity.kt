package com.example.proyecto

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.objetos.TaskManager

class ShowTasksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_tasks)
        val tasksListView = findViewById<ListView>(R.id.tasksListView)

        // Obtener la lista de tareas desde TaskManager
        val tasks = TaskManager.getAllTasks()

        // Crear una lista de strings con los nombres y descripciones de las tareas
        val taskDescriptions = tasks.map { "${it.title}: ${it.description}" }

        // Crear un adaptador para el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, taskDescriptions)

        // Establecer el adaptador en el ListView
        tasksListView.adapter = adapter
    }
}