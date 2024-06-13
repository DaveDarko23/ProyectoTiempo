package com.example.proyecto

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PruebaActivity : AppCompatActivity() {
    private lateinit var unselectedListView: ListView
    private lateinit var selectedListView: ListView

    private val unselectedWorkers = mutableListOf("Trabajador 1", "Trabajador 2", "Trabajador 3")
    private val selectedWorkers = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_prueba)
        unselectedListView = findViewById(R.id.unselectedListView)
        selectedListView = findViewById(R.id.selectedListView)

        // Crear adaptadores para las listas
        val unselectedAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, unselectedWorkers)
        val selectedAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, selectedWorkers)

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

        selectedListView.setOnItemClickListener { _, _, position, _ ->
            val worker = selectedWorkers[position]
            unselectedWorkers.add(worker)
            selectedWorkers.remove(worker)
            unselectedAdapter.notifyDataSetChanged()
            selectedAdapter.notifyDataSetChanged()
        }
    }
}