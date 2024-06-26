package com.example.pryandroidclinica

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pryandroidclinica.retrofit.ApiService
import com.example.pryandroidclinica.retrofit.RetrofitClient
import com.example.pryandroidclinica.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ValidacionEmailActivity : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var btnRecuperar: Button
    private val TAG = "ValidacionEmailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.validacion_email_layout)

        txtEmail = findViewById(R.id.txtEmail)
        btnRecuperar = findViewById(R.id.RecuperarButton)

        btnRecuperar.setOnClickListener {
            val email = txtEmail.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese su correo electrónico", Toast.LENGTH_SHORT).show()
            } else {
                enviarCodigoRecuperacion(email)
            }
        }
    }

    private fun enviarCodigoRecuperacion(email: String) {
        val apiService = RetrofitClient.createService()
        val call = apiService.enviarCodigoRecuperacion(email)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.isStatus) {
                        Log.d(TAG, "Código de verificación enviado correctamente")
                        val intent = Intent(this@ValidacionEmailActivity, CodigoValidacionActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                    } else {
                        val message = response.body()!!.message ?: "Error desconocido en la respuesta de la API"
                        Log.e(TAG, "Error en la respuesta de la API: $message")
                        Toast.makeText(this@ValidacionEmailActivity, message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = if (errorBody != null && errorBody.isNotEmpty()) {
                        "Error al enviar el código de verificación: $errorBody"
                    } else {
                        "Error al enviar el código de verificación, respuesta no exitosa"
                    }
                    Log.e(TAG, message)
                    Toast.makeText(this@ValidacionEmailActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                val message = "Error de red al intentar enviar el código de verificación: ${t.message}"
                Log.e(TAG, message, t)
                Toast.makeText(this@ValidacionEmailActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
