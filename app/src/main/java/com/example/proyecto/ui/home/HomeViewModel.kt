package com.example.proyecto.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto.objetos.Administrator
import com.example.proyecto.objetos.CurrentUserManager
import com.example.proyecto.objetos.Task
import com.example.proyecto.objetos.TaskManager
import com.example.proyecto.objetos.Worker

class HomeViewModel : ViewModel() {

    private val _lowPriorityTasks = MutableLiveData<List<Task>>()
    val lowPriorityTasks: LiveData<List<Task>>
        get() = _lowPriorityTasks

    init {
        loadLowPriorityTasks()
    }

    private fun loadLowPriorityTasks() {
        // Obtener al usuario actual (asumiendo que está logueado)
        val currentUser = CurrentUserManager.getCurrentUser() // Aquí obtén el usuario actual correctamente

        // Verificar si el usuario es un Worker o un Administrator
        when (currentUser) {
            is Worker -> {
                // Obtener la lista de tareas en las que el trabajador está trabajando
                val workingTasks = currentUser.workingList

                // Obtener los IDs de las tareas
                val taskIds = workingTasks.map { it.idTask }

                // Obtener las tareas correspondientes de TaskManager con prioridad "low"
                val lowPriorityTasks = taskIds.mapNotNull { id -> TaskManager.getTaskById(id) }
                    .filter { it.priority.equals("low", ignoreCase = true) }

                // Actualizar el LiveData
                _lowPriorityTasks.postValue(lowPriorityTasks)
            }
            is Administrator -> {
                // Obtener todas las tareas con prioridad "low" del TaskManager
                val lowPriorityTasks = TaskManager.getAllTasks()
                    .filter { it.priority.equals("low", ignoreCase = true) }

                // Actualizar el LiveData
                _lowPriorityTasks.postValue(lowPriorityTasks)
            }
            else -> {
                // Manejar caso donde el usuario no es ni Worker ni Administrator si es necesario
                _lowPriorityTasks.postValue(emptyList())
            }
        }
    }
}
