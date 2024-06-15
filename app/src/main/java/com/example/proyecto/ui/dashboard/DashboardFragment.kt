package com.example.proyecto.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.TaskActivity
import com.example.proyecto.ViewTaskActivity
import com.example.proyecto.adapters.TaskAdapter
import com.example.proyecto.databinding.FragmentDashboardBinding
import com.example.proyecto.databinding.FragmentHomeBinding
import com.example.proyecto.objetos.CurrentTaskManager
import com.example.proyecto.objetos.CurrentUserManager
import com.example.proyecto.ui.home.HomeViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var taskRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        // Configurar RecyclerView
        taskRecyclerView = binding.taskMediumRecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Configurar ViewModel
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        // Observar cambios en la lista de tareas con prioridad "low"
        dashboardViewModel.mediumPriorityTasks.observe(viewLifecycleOwner, Observer { tasks ->
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