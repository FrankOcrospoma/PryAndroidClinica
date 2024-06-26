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

class CodigoValidacionActivity : AppCompatActivity() {

    private lateinit var btnValidar: Button
    private lateinit var digito1: EditText
    private lateinit var digito2: EditText
    private lateinit var digito3: EditText
    private lateinit var digito4: EditText
    private lateinit var digito5: EditText
    private lateinit var digito6: EditText
    private val TAG = "CodigoValidacionActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.codigo_validacion_layout)

        btnValidar = findViewById(R.id.btnValidar)
        digito1 = findViewById(R.id.digito1)
        digito2 = findViewById(R.id.digito2)
        digito3 = findViewById(R.id.digito3)
        digito4 = findViewById(R.id.digito4)
        digito5 = findViewById(R.id.digito5)
        digito6 = findViewById(R.id.digito6)

        btnValidar.setOnClickListener {
            val email = intent.getStringExtra("email") ?: return@setOnClickListener
            val codigo = "${digito1.text}${digito2.text}${digito3.text}${digito4.text}${digito5.text}${digito6.text}"
            verificarCodigo(email, codigo)
        }
    }

    private fun verificarCodigo(email: String, codigo: String) {
        val apiService = RetrofitClient.createService()
        val call = apiService.verificarCodigo(email, codigo)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()!!.isStatus) {
                        Log.d(TAG, "Código de verificación correcto")
                        val intent = Intent(this@CodigoValidacionActivity, RestablecerContrasenaActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                    } else {
                        val message = response.body()!!.message ?: "Código incorrecto"
                        Log.e(TAG, "Error1 en la verificación: $message")
                        Toast.makeText(this@CodigoValidacionActivity, message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val message = if (errorBody != null && errorBody.isNotEmpty()) {
                        "CÓDIGO INCORRECTO"
                    } else {
                        "Error3 al verificar el código, respuesta no exitosa"
                    }
                    Log.e(TAG, message)
                    Toast.makeText(this@CodigoValidacionActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                val message = "Error de red al intentar verificar el código: ${t.message}"
                Log.e(TAG, message, t)
                Toast.makeText(this@CodigoValidacionActivity, message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
