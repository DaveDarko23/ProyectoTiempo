package com.example.proyecto.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto.objetos.Administrator
import com.example.proyecto.objetos.CurrentUserManager
import com.example.proyecto.objetos.Task
import com.example.proyecto.objetos.TaskManager
import com.example.proyecto.objetos.Worker

class NotificationsViewModel : ViewModel() {

    private val _highPriorityTasks = MutableLiveData<List<Task>>()
    val highPriorityTasks: LiveData<List<Task>>
        get() = _highPriorityTasks

    init {
        loadHighPriorityTasks()
    }

    private fun loadHighPriorityTasks() {
        // Obtener al usuario actual (asumiendo que está logueado)
        val currentUser = CurrentUserManager.getCurrentUser() // Aquí obtén el usuario actual correctamente

        // Verificar si el usuario es un Worker o un Administrator
        when (currentUser) {
            is Worker -> {
                // Obtener la lista de tareas en las que el trabajador está trabajando
                val workingTasks = currentUser.workingList

                // Obtener los IDs de las tareas
                val taskIds = workingTasks.map { it.idTask }

                // Obtener las tareas correspondientes de TaskManager con prioridad "high"
                val highPriorityTasks = taskIds.mapNotNull { id -> TaskManager.getTaskById(id) }
                    .filter { it.priority.equals("high", ignoreCase = true) }

                // Actualizar el LiveData
                _highPriorityTasks.postValue(highPriorityTasks)
            }
            is Administrator -> {
                // Obtener todas las tareas con prioridad "high" del TaskManager
                val highPriorityTasks = TaskManager.getAllTasks()
                    .filter { it.priority.equals("high", ignoreCase = true) }

                // Actualizar el LiveData
                _highPriorityTasks.postValue(highPriorityTasks)
            }
            else -> {
                // Manejar caso donde el usuario no es ni Worker ni Administrator si es necesario
                _highPriorityTasks.postValue(emptyList())
            }
        }
    }
}
