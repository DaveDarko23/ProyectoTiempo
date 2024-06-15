package com.example.proyecto.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.TaskActivity
import com.example.proyecto.ViewTaskActivity
import com.example.proyecto.adapters.TaskAdapter
import com.example.proyecto.databinding.FragmentNotificationsBinding
import com.example.proyecto.objetos.CurrentTaskManager
import com.example.proyecto.objetos.CurrentUserManager

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var taskRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        taskRecyclerView = binding.taskHighRecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        notificationsViewModel.highPriorityTasks.observe(viewLifecycleOwner) { tasks ->
            if (tasks != null) {
                val adapter = TaskAdapter(tasks) { selectedTask ->
                    CurrentTaskManager.setCurrentTask(selectedTask)
                    if (CurrentUserManager.getCurrentUser()?.userType === "Worker") {
                        val intent = Intent(requireContext(), TaskActivity::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(requireContext(), ViewTaskActivity::class.java)
                        startActivity(intent)
                    }
                }
                taskRecyclerView.adapter = adapter
            } else {
                Toast.makeText(requireContext(), "No tasks available", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
