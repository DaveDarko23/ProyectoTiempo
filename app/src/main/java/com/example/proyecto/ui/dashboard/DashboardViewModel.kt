package com.example.proyecto.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto.objetos.Administrator
import com.example.proyecto.objetos.CurrentUserManager
import com.example.proyecto.objetos.Task
import com.example.proyecto.objetos.TaskManager
import com.example.proyecto.objetos.Worker

class DashboardViewModel : ViewModel() {

    private val _mediumPriorityTasks = MutableLiveData<List<Task>>()
    val mediumPriorityTasks: LiveData<List<Task>>
        get() = _mediumPriorityTasks

    init {
        loadMediumPriorityTasks()
    }

    private fun loadMediumPriorityTasks() {
        // Obtener al usuario actual (asumiendo que está logueado)
        val currentUser = CurrentUserManager.getCurrentUser() // Aquí obtén el usuario actual correctamente

        // Verificar si el usuario es un Worker o un Administrator
        when (currentUser) {
            is Worker -> {
                // Obtener la lista de tareas en las que el trabajador está trabajando
                val workingTasks = currentUser.workingList

                // Obtener los IDs de las tareas
                val taskIds = workingTasks.map { it.idTask }

                // Obtener las tareas correspondientes de TaskManager con prioridad "medium"
                val mediumPriorityTasks = taskIds.mapNotNull { id -> TaskManager.getTaskById(id) }
                    .filter { it.priority.equals("medium", ignoreCase = true) }

                // Actualizar el LiveData
                _mediumPriorityTasks.postValue(mediumPriorityTasks)
            }
            is Administrator -> {
                // Obtener todas las tareas con prioridad "medium" del TaskManager
                val mediumPriorityTasks = TaskManager.getAllTasks()
                    .filter { it.priority.equals("medium", ignoreCase = true) }

                // Actualizar el LiveData
                _mediumPriorityTasks.postValue(mediumPriorityTasks)
            }
            else -> {
                // Manejar caso donde el usuario no es ni Worker ni Administrator si es necesario
                _mediumPriorityTasks.postValue(emptyList())
            }
        }
    }
}
