package com.example.proyecto.objetos

object TaskManager {
    private val tasks = mutableListOf<Task>()

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun removeTask(taskId: Int) {
        val iterator = tasks.iterator()
        while (iterator.hasNext()) {
            val task = iterator.next()
            if (task.id == taskId) {
                iterator.remove()
                return
            }
        }
    }

    fun getTask(taskId: Int): Task? {
        return tasks.find { it.id == taskId }
    }

    fun getTaskSize(): Int {
        return tasks.size
    }

    fun getAllTasks(): List<Task> {
        return tasks
    }

    fun getTaskById(id: Int): Task? {
        return tasks.find { it.id == id }
    }

    fun updateTaskTime(taskId: Int, time: Long) {
        val task = tasks.find { it.id == taskId }
        task?.let {
            it.timeSpent += time
        }
    }
}
