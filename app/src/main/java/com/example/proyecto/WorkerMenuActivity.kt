package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WorkerMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_worker_menu)

        val myTaskButton = findViewById<Button>(R.id.myTasksButton)

        myTaskButton.setOnClickListener {
            val intent = Intent(this, WorkerTaskListActivity::class.java)
            startActivity(intent)
        }

    }
}