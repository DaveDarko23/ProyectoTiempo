package com.example.proyecto.objetos
object CurrentTaskManager {
    private var currentTask: Task? = null

    fun setCurrentTask(task: Task) {
        currentTask = task
    }

    fun getCurrentTask(): Task? {
        return currentTask
    }

    fun clearCurrentTask() {
        currentTask = null
    }
}
