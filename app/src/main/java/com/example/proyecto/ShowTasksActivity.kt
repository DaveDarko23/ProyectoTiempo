package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.objetos.Task
import com.example.proyecto.objetos.TaskManager
import com.example.proyecto.objetos.CurrentTaskManager

class ShowTasksActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var tasksListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_tasks)
        tasksListView = findViewById(R.id.tasksListView)

        // Obtener la lista de tareas desde TaskManager
        val tasks = TaskManager.getAllTasks()

        // Crear una lista de strings con los nombres y descripciones de las tareas
        val taskDescriptions = tasks.map { "${it.title}: ${it.description}" }

        // Crear un adaptador para el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, taskDescriptions)

        // Establecer el adaptador en el ListView
        tasksListView.adapter = adapter

        // Configurar el listener para los clics en los elementos del ListView
        tasksListView.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
        // Obtener la tarea seleccionada desde TaskManager según la posición
        val selectedTask = TaskManager.getTask(position) as Task

        // Establecer la tarea actual en CurrentTaskManager
        CurrentTaskManager.setCurrentTask(selectedTask)

        // Crear un intent para abrir TaskActivity
        val intent = Intent(this, ViewTaskActivity::class.java)
        startActivity(intent)
    }
}
