package com.example.proyecto

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.objetos.Comment
import com.example.proyecto.objetos.CurrentTaskManager
import com.example.proyecto.objetos.CurrentUserManager
import com.example.proyecto.objetos.Task
import com.example.proyecto.objetos.TaskManager
import com.example.proyecto.objetos.Worker
import com.example.proyecto.objetos.Working
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskActivity : AppCompatActivity() {
    private lateinit var commentListView: ListView
    private lateinit var commentEditText: EditText
    private lateinit var addCommentButton: Button
    private lateinit var timerButton: Button
    private lateinit var elapsedTimeTextView: TextView
    private lateinit var personalTimeTextView: TextView

    private var isTimerRunning = false
    private var elapsedTimeSeconds: Long = 0
    private var handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var lastPauseTimeMillis: Long = 0

    var task: Task? = null
    var currentUser: Worker? = null
    var working: Working? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        // Obtener la tarea actual desde CurrentTaskManager
        task = CurrentTaskManager.getCurrentTask()
        currentUser = CurrentUserManager.getCurrentUser() as Worker
        working = currentUser!!.workingList.find { it.idTask == task!!.id }

        // Mostrar los detalles de la tarea (ajusta según tu layout)
        findViewById<TextView>(R.id.taskTitleTextView).text = task?.title
        findViewById<TextView>(R.id.taskDescriptionTextView).text = task?.description

        // Configurar ListView para los comentarios
        commentListView = findViewById(R.id.commentListView)
        commentEditText = findViewById(R.id.commentEditText)
        addCommentButton = findViewById(R.id.addCommentButton)
        timerButton = findViewById(R.id.timerButton)
        elapsedTimeTextView = findViewById(R.id.elapsedTimeTextView)
        personalTimeTextView = findViewById(R.id.personalTimeTextView)

        personalTimeTextView.text = formatElapsedTime(task!!.timeSpent)

        // Configurar adaptador para los comentarios
        task?.let { currentTask ->
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                currentTask.comments.map { comment ->
                    formatComment(comment)
                })
            commentListView.adapter = adapter

            // Agregar un nuevo comentario
            addCommentButton.setOnClickListener {
                val newCommentText = commentEditText.text.toString()
                if (newCommentText.isNotEmpty()) {
                    val calendar = Calendar.getInstance()
                    val newComment = Comment(
                        id = currentTask.comments.size,  // Usar el tamaño actual de la lista como ID
                        comentario = newCommentText,
                        fecha = calendar.time,
                        hora = calendar.time
                    )
                    currentTask.comments.add(newComment)
                    adapter.add(formatComment(newComment))  // Agregar el comentario formateado al adaptador
                    adapter.notifyDataSetChanged()  // Notificar al adaptador que los datos han cambiado
                    commentEditText.text.clear()  // Limpiar el campo de texto del comentario
                }
            }

            // Configurar el botón de timer para iniciar o detener el contador
            timerButton.setOnClickListener {
                if (isTimerRunning) {
                    stopTimer()
                } else {
                    startTimer()
                }
            }

            // Mostrar el tiempo personal dedicado
            updatePersonalTimeTextView()
        }
    }

    private fun startTimer() {
        isTimerRunning = true
        timerButton.text = "Detener Timer"
        runnable = object : Runnable {
            override fun run() {
                elapsedTimeSeconds++
                val timeFormat = formatElapsedTime(elapsedTimeSeconds)
                elapsedTimeTextView.text = timeFormat
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }

    private fun stopTimer() {
        isTimerRunning = false
        timerButton.text = "Iniciar Timer"
        handler.removeCallbacks(runnable)
        updatePersonalTime()
        updatePersonalTimeTextView()
        TaskManager.updateTaskTime(task!!.id, elapsedTimeSeconds)
        elapsedTimeSeconds = 0 // Resetear el temporizador después de actualizar el tiempo personal
    }

    private fun updatePersonalTimeTextView() {
        personalTimeTextView.text = "Tiempo dedicado: ${formatElapsedTime(working!!.personalTime)}"
    }

    private fun formatElapsedTime(seconds: Long): String {
        val hours = (seconds / 3600).toInt()
        val minutes = ((seconds % 3600) / 60).toInt()
        val remainingSeconds = (seconds % 60).toInt()
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
    }

    private fun updatePersonalTime() {
        working!!.personalTime += elapsedTimeSeconds
    }

    // Función para formatear el comentario para mostrarlo en el ListView
    private fun formatComment(comment: Comment): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormat.format(comment.fecha)
        val formattedTime = timeFormat.format(comment.hora)
        return "$formattedDate - $formattedTime: ${comment.comentario}"
    }

    override fun onPause() {
        super.onPause()
        if (isTimerRunning) {
            stopTimer()
            lastPauseTimeMillis = System.currentTimeMillis()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isTimerRunning && lastPauseTimeMillis > 0) {
            val currentTimeMillis = System.currentTimeMillis()
            val timePassedMillis = currentTimeMillis - lastPauseTimeMillis
            elapsedTimeSeconds += timePassedMillis / 1000
            startTimer()
        }
    }
}
