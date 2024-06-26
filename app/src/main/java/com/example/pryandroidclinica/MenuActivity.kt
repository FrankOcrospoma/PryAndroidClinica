package com.example.pryandroidclinica

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.pryandroidclinica.databinding.ActivityMenu2Binding
import com.example.pryandroidclinica.retrofit.RetrofitClient
import com.google.android.material.navigation.NavigationView

class MenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenu2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenu2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMenu2.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu2)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_notificacion, R.id.nav_manage_patients, R.id.nav_manage_appointments, R.id.nav_citas, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Recuperar y mostrar el nombre y correo del usuario en el header del menú
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val nombreUsuario = sharedPreferences.getString("nombreUsuario", "Jane Doe")
        val username = sharedPreferences.getString("username", "Jane Doe")
        val correoUsuario = sharedPreferences.getString("email", "jane.doe@example.com")
        val fotoUsuario = sharedPreferences.getString("foto", "")
        val apellido = sharedPreferences.getString("ape_completo", "Doe")
        val headerView = navView.getHeaderView(0)
        val navHeaderTitle: TextView = headerView.findViewById(R.id.nav_header_title)
        val navHeaderSubtitle: TextView = headerView.findViewById(R.id.nav_header_subtitle)
        val navHeaderImageView: ImageView = headerView.findViewById(R.id.imageView)

        navHeaderTitle.text = nombreUsuario +" "+ apellido
        navHeaderSubtitle.text = correoUsuario

        // Cargar la imagen de perfil usando Glide
        val imageUrl = RetrofitClient.URL_API_SERVICE + "/" + (fotoUsuario ?: "")
        Glide.with(this)
            .load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    // Manejar error al cargar la imagen
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // La imagen se cargó correctamente
                    return false
                }
            })
            .into(navHeaderImageView)


       // val txtNombreUsuario: TextView = findViewById(R.id.txtNombreUsuario)
     //   txtNombreUsuario.text = nombreUsuario

        // Configurar el clic en el botón "Ver Usuario"
        val btnVerUsuario: Button = headerView.findViewById(R.id.btnVerUsuario)
        btnVerUsuario.setOnClickListener {UserProfileActivity
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
