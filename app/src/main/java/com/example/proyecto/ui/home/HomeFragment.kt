package com.example.proyecto.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.TaskActivity
import com.example.proyecto.ViewTaskActivity
import com.example.proyecto.adapters.TaskAdapter
import com.example.proyecto.databinding.FragmentHomeBinding
import com.example.proyecto.objetos.CurrentTaskManager
import com.example.proyecto.objetos.CurrentUserManager
import com.example.proyecto.objetos.Task

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var taskRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configurar RecyclerView
        taskRecyclerView = binding.taskLowRecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Configurar ViewModel
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Observar cambios en la lista de tareas con prioridad "low"
        homeViewModel.lowPriorityTasks.observe(viewLifecycleOwner, Observer { tasks ->
            // Actualizar el RecyclerView con las tareas recibidas
            val adapter = TaskAdapter(tasks) { selectedTask ->
                CurrentTaskManager.setCurrentTask(selectedTask)
                // Aquí inicia la actividad para mostrar detalles de la tarea
                if (CurrentUserManager.getCurrentUser()?.userType === "Worker") {
                    val intent = Intent(requireContext(), TaskActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(requireContext(), ViewTaskActivity::class.java)
                    startActivity(intent)
                }
            }
            taskRecyclerView.adapter = adapter
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
