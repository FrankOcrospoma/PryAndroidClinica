package com.example.pryandroidclinica

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

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
                // Lógica para restablecer la contraseña
                Toast.makeText(this, "Contraseña restablecida correctamente", Toast.LENGTH_SHORT).show()
                // Finalizar la actividad
                finish()
            }
        }
    }
}
