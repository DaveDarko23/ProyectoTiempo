package com.example.proyecto

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adapters.CommentAdapter
import com.example.proyecto.objetos.Comment
import com.example.proyecto.objetos.CurrentTaskManager
import com.example.proyecto.objetos.Task
import java.text.SimpleDateFormat
import java.util.Locale

class ViewTaskActivity : AppCompatActivity() {
    private lateinit var commentRecyclerView: RecyclerView
    private lateinit var taskTitleTextView: TextView
    private lateinit var taskDescriptionTextView: TextView
    private lateinit var taskTimeSpentTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task)

        // Obtener la tarea actual desde CurrentTaskManager
        val task: Task? = CurrentTaskManager.getCurrentTask()

        // Mostrar los detalles de la tarea (ajusta según tu layout)
        taskTitleTextView = findViewById(R.id.taskTitleTextView)
        taskDescriptionTextView = findViewById(R.id.taskDescriptionTextView)
        taskTimeSpentTextView = findViewById(R.id.taskTimeSpentTextView)
        commentRecyclerView = findViewById(R.id.commentRecyclerView)

        taskTitleTextView.text = task?.title
        taskDescriptionTextView.text = task?.description
        taskTimeSpentTextView.text = formatElapsedTime(task?.timeSpent ?: 0)

        // Configurar RecyclerView para los comentarios
        task?.let { currentTask ->
            commentRecyclerView.layoutManager = LinearLayoutManager(this)
            commentRecyclerView.adapter = CommentAdapter(currentTask.comments)
        }
    }

    // Función para formatear el tiempo transcurrido en horas, minutos y segundos
    private fun formatElapsedTime(seconds: Long): String {
        val hours = (seconds / 3600).toInt()
        val minutes = ((seconds % 3600) / 60).toInt()
        val remainingSeconds = (seconds % 60).toInt()
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
    }
}