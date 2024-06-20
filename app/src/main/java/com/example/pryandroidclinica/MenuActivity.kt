package com.example.pryandroidclinica

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.pryandroidclinica.databinding.ActivityMenu2Binding

class MenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenu2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenu2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMenu2.toolbar)

        binding.appBarMenu2.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu2)
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Recuperar y mostrar el nombre y correo del usuario en el header del menú
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val nombreUsuario = sharedPreferences.getString("nombreUsuario", "Jane Doe")
        val correoUsuario = sharedPreferences.getString("email", "jane.doe@example.com")


        val nombreUsuario2 = sharedPreferences.getString("nombreUsuario", "Jane Doe")
        val txtNombreUsuario: TextView = findViewById(R.id.txtNombreUsuario)
        txtNombreUsuario.text = nombreUsuario2

        val headerView = navView.getHeaderView(0)
        val navHeaderTitle: TextView = headerView.findViewById(R.id.nav_header_title)
        val navHeaderSubtitle: TextView = headerView.findViewById(R.id.nav_header_subtitle)
        navHeaderTitle.text = nombreUsuario
        navHeaderSubtitle.text = correoUsuario

        // Configurar el clic en el botón "Ver Usuario"
        val btnVerUsuario: Button = headerView.findViewById(R.id.btnVerUsuario)
        btnVerUsuario.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu2)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
