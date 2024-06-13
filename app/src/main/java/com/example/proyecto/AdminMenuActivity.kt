package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdminMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_menu)

        val createWorkersButton = findViewById<Button>(R.id.createWorkersButton)
        val createTasksButton = findViewById<Button>(R.id.createTasksButton)
        val showUsersButton = findViewById<Button>(R.id.showUsersButton)
        val showTasksButton = findViewById<Button>(R.id.showTasksButton)

        createWorkersButton.setOnClickListener {
            val intent = Intent(this, CreateWorkersActivity::class.java)
            startActivity(intent)
        }

        createTasksButton.setOnClickListener {
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }

        showUsersButton.setOnClickListener {
            val intent = Intent(this, ShowUsersActivity::class.java)
            startActivity(intent)
        }

        showTasksButton.setOnClickListener {
            val intent = Intent(this, ShowTasksActivity::class.java)
            startActivity(intent)
        }
    }
}