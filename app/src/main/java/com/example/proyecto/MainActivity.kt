package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.objetos.Administrator
import com.example.proyecto.objetos.CurrentUserManager
import com.example.proyecto.objetos.UserManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Comprobar si hay usuarios registrados
        if (UserManager.getUsers().isEmpty()) {
            // Si no hay usuarios, crear un administrador predeterminado
            val defaultAdmin = Administrator(
                0,
                "Admin",
                "Admin",
                "admin123",
                "Admin",
                "IT",
                "Manage IT infrastructure",
                "Full-time"
            )
            UserManager.addUser(defaultAdmin)
        }
        /*val intent = Intent(this, PruebaActivity::class.java)
        startActivity(intent)*/

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val user = UserManager.findUserByUsername(username)
            if (user != null && user.password == password) {
                CurrentUserManager.setCurrentUser(user)
                if (user.userType.equals("Admin")) {
                    val intent = Intent(this, AdminMenuActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, WorkerMenuActivity::class.java)
                    startActivity(intent)
                }
            } else {
                // Fallo en la autenticación
                Toast.makeText(
                    this,
                    "Nombre de usuario o contraseña incorrectos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}