package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proyecto.databinding.ActivityTaskListNavigationBinding
import com.example.proyecto.objetos.Administrator
import com.example.proyecto.objetos.CurrentUserManager

class TaskListNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskListNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskListNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Toolbar
        setSupportActionBar(binding.toolbar)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_task_list_navigation)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val currentUser = CurrentUserManager.getCurrentUser()
        return if (currentUser is Administrator) {
            menuInflater.inflate(R.menu.menu_task_list_navigation, menu)
            true
        } else {
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_create_user -> {
                val intent = Intent(this, CreateWorkersActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_create_task -> {
                val intent = Intent(this, CreateTaskActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_show_users -> {
                val intent = Intent(this, ShowUsersActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
