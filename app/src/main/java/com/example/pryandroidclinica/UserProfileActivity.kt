package com.example.pryandroidclinica

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_layout)

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
    }
}