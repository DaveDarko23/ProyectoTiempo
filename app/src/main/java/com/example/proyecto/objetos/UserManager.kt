package com.example.proyecto.objetos

object UserManager {
    private val users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    fun removeUser(user: User) {
        users.remove(user)
    }

    fun getUsers(): List<User> {
        return users
    }

    fun getUsersSize(): Int {
        return users.size
    }

    fun findUserByUsername(username: String): User? {
        return users.find { it.username == username }
    }
}
