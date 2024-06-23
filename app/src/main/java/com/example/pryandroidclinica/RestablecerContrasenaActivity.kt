package com.example.pryandroidclinica

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pryandroidclinica.response.LoginResponse
import com.example.pryandroidclinica.retrofit.ApiService
import com.example.pryandroidclinica.retrofit.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class RestablecerContrasenaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.restablecer_contrasena_layout)

        val nuevaContrasena = findViewById<TextInputEditText>(R.id.txtNuevaC)
        val confirmarContrasena = findViewById<TextInputEditText>(R.id.txtConfirmarC)
        val btnRestablecer = findViewById<Button>(R.id.btnRestablecer)

        // Manejar el clic en el botón de retroceso
        findViewById<ImageButton>(R.id.btn_retroceder).setOnClickListener {
            onBackPressed()
        }

        btnRestablecer.setOnClickListener {
            val nueva = nuevaContrasena.text.toString()
            val confirmar = confirmarContrasena.text.toString()

            if (nueva.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else if (nueva != confirmar) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                // Encriptar la nueva contraseña en MD5
                val nuevaEncriptada = encriptarMD5(nueva)

                // Obtener el ID del usuario desde SharedPreferences
                val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                val id = sharedPreferences.getInt("id", 1)

                // Llamar a la API para cambiar la contraseña
                val apiService = RetrofitClient.createService()
                val call = apiService.cambiarContrasena(id, nuevaEncriptada)
                call.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful && response.body()?.isStatus == true) {
                            Toast.makeText(this@RestablecerContrasenaActivity, "Contraseña restablecida correctamente", Toast.LENGTH_SHORT).show()
                            // Finalizar la actividad
                            finish()
                        } else {
                            Toast.makeText(this@RestablecerContrasenaActivity, "Error al restablecer la contraseña", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@RestablecerContrasenaActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun encriptarMD5(input: String): String {
        return try {
            val md = MessageDigest.getInstance("MD5")
            val byteArray = md.digest(input.toByteArray())
            val sb = StringBuilder()
            for (byte in byteArray) {
                sb.append(String.format("%02x", byte))
            }
            sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }
}
