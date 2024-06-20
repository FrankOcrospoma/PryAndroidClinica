package com.example.pryandroidclinica

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class UserProfileActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_layout)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment_content_profile)  // Ajusta el ID según corresponda
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Obtener los datos del usuario desde SharedPreferences
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val nombreUsuario = sharedPreferences.getString("nombreUsuario", "Nombre Usuario")
        val fechaNacimiento = sharedPreferences.getString("fechaNacimiento", "Fecha Nacimiento")
        val documento = sharedPreferences.getString("documento", "Documento")
        val email = sharedPreferences.getString("email", "Email")
        val direccion = sharedPreferences.getString("direccion", "Direccion")
        val telefono = sharedPreferences.getString("telefono", "Telefono")

        // Establecer los datos en las vistas
        findViewById<TextView>(R.id.nombre_completo_perfil_usuario).text = nombreUsuario
        findViewById<TextView>(R.id.txtPerfilNickUsuario).text = nombreUsuario
        findViewById<TextView>(R.id.txtPerfilFechaNacimiento).text = fechaNacimiento
        findViewById<TextView>(R.id.txtPerfilDocumento).text = documento
        findViewById<TextView>(R.id.txtPerfilEmail).text = email
        findViewById<TextView>(R.id.txtPerfilDireccion).text = direccion
        findViewById<TextView>(R.id.txtPerfilTelefono).text = telefono

        // Configurar el clic en el icono del menú
        findViewById<TextView>(R.id.nombreClinica).setOnClickListener {
            drawerLayout.openDrawer(navView)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_profile)  // Ajusta el ID según corresponda
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}