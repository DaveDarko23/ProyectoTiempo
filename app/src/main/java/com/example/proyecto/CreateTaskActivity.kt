package com.example.proyecto

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.objetos.Task
import com.example.proyecto.objetos.TaskManager
import com.example.proyecto.objetos.UserManager
import com.example.proyecto.objetos.Worker
import com.example.proyecto.objetos.Working

class CreateTaskActivity : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var createTimeButton: Button
    private lateinit var unselectedListView: ListView
    private lateinit var selectedListView: ListView

    private val unselectedWorkers = mutableListOf<String>()
    private val selectedWorkers = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_task)
        titleEditText = findViewById(R.id.titleEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        prioritySpinner = findViewById(R.id.prioritySpinner)
        createTimeButton = findViewById(R.id.createTimeButton)
        unselectedListView = findViewById(R.id.unselectedListView)
        selectedListView = findViewById(R.id.selectedListView)

        // Configurar el spinner de prioridad
        val priorityOptions = arrayOf("Low", "Medium", "High")
        val priorityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorityOptions)
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        prioritySpinner.adapter = priorityAdapter

        // Crear adaptadores para las listas
        val unselectedAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, unselectedWorkers)
        val selectedAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, selectedWorkers)

        // Asignar adaptadores a las listas
        unselectedListView.adapter = unselectedAdapter
        selectedListView.adapter = selectedAdapter

        // Manejar la transferencia de trabajadores al hacer clic en un trabajador no seleccionado
        unselectedListView.setOnItemClickListener { _, _, position, _ ->
            val worker = unselectedWorkers[position]
            selectedWorkers.add(worker)
            unselectedWorkers.remove(worker)
            unselectedAdapter.notifyDataSetChanged()
            selectedAdapter.notifyDataSetChanged()
        }

        // Manejar la transferencia de trabajadores al hacer clic en un trabajador seleccionado
        selectedListView.setOnItemClickListener { _, _, position, _ ->
            val worker = selectedWorkers[position]
            unselectedWorkers.add(worker)
            selectedWorkers.remove(worker)
            unselectedAdapter.notifyDataSetChanged()
            selectedAdapter.notifyDataSetChanged()
        }

        createTimeButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val priority = prioritySpinner.selectedItem.toString()

            // Verificar que los campos no estén vacíos
            if (title.isNotEmpty() && description.isNotEmpty()) {
                // Crear una nueva tarea
                val newTask = Task(
                    id = TaskManager.getTaskSize(), // Generar un ID único para la tarea
                    title = title,
                    description = description,
                    priority = priority
                )

                selectedWorkers.forEach { worker ->
                    val user = UserManager.findUserByUsername(worker)
                    if (user is Worker) {
                        // Crear un nuevo objeto Working y agregarlo a la workingList del Worker
                        val newWorking = Working(idTask = TaskManager.getTaskSize())
                        user.workingList.add(newWorking)
                    }
                }
                TaskManager.addTask(newTask)

                // Mostrar mensaje o realizar otra acción después de asignar las tareas
                Toast.makeText(this, "Tareas asignadas exitosamente a los trabajadores seleccionados", Toast.LENGTH_SHORT).show()

                // Limpiar los campos de título, descripción y prioridad después de crear la tarea
                titleEditText.setText("")
                descriptionEditText.setText("")
                prioritySpinner.setSelection(0) // Seleccionar la primera opción (Low)

                // Limpiar la lista de trabajadores seleccionados
                selectedWorkers.clear()
                selectedAdapter.notifyDataSetChanged()

                unselectedWorkers.clear()
                unselectedWorkers.addAll(UserManager.getUsers().map { it.username })
                unselectedAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // Obtener los trabajadores de UserManager y agregarlos a la lista de trabajadores no seleccionados
        unselectedWorkers.addAll(UserManager.getUsers().map { it.username })
        unselectedAdapter.notifyDataSetChanged()
    }
}
