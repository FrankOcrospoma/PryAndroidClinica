package com.example.pryandroidclinica

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pryandroidclinica.response.CitasResponse
import com.example.pryandroidclinica.response.OdontologosResponse
import com.example.pryandroidclinica.response.PacientesResponse
import com.example.pryandroidclinica.retrofit.ApiService
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.widget.Button
import android.widget.EditText

data class Paciente(val id: Int, val nombre: String) {
    override fun toString(): String {
        return nombre
    }
}

data class Odontologo(val id: Int, val nombre: String) {
    override fun toString(): String {
        return nombre
    }
}

class CustomArrayAdapter(context: Context, private val items: List<Any>) : ArrayAdapter<Any>(context, android.R.layout.simple_spinner_item, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        (view as TextView).text = getItem(position).toString()
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        (view as TextView).text = getItem(position).toString()
        return view
    }
}

class CitasActivity : AppCompatActivity() {

    private lateinit var spinnerPaciente: Spinner
    private lateinit var spinnerOdontologo: Spinner
    private lateinit var txtAgregarFechaCita: TextView
    private lateinit var txtAgregarHoraCita: TextView
    private lateinit var txtAgregarMotivoCita: EditText
    private lateinit var btnConfirmar: Button

    private lateinit var pacientesList: List<Paciente>
    private lateinit var odontologosList: List<Odontologo>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agregar_cita_layout)

        spinnerPaciente = findViewById(R.id.spinnerPaciente)
        spinnerOdontologo = findViewById(R.id.spinnerOdontologo)
        txtAgregarFechaCita = findViewById(R.id.txtAgregarFechaCita)
        txtAgregarHoraCita = findViewById(R.id.inputHora)
        txtAgregarMotivoCita = findViewById(R.id.txtAgregarMotivoCita)
        btnConfirmar = findViewById(R.id.crearcita)

        txtAgregarFechaCita.setOnClickListener {
            showDatePickerDialog()
        }

        txtAgregarHoraCita.setOnClickListener {
            showTimePickerDialog()
        }

        btnConfirmar.setOnClickListener {
            registrarCita()
        }

        cargarDatosSpinners()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                txtAgregarFechaCita.text = dateFormat.format(selectedDate.time)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                txtAgregarHoraCita.text = selectedTime
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }

    private fun registrarCita() {
        val paciente = spinnerPaciente.selectedItem as Paciente
        val odontologo = spinnerOdontologo.selectedItem as Odontologo

        val pacienteId = paciente.id
        val odontologoId = odontologo.id

        val fecha = txtAgregarFechaCita.text.toString()
        val hora = txtAgregarHoraCita.text.toString()
        val motivoConsulta = txtAgregarMotivoCita.text.toString()

        // Convertir fecha a formato YYYY-MM-DD
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(fecha)
        val formattedDate = outputFormat.format(date!!)

        val apiService = RetrofitClient.createService()
        val call = apiService.registrarCita(pacienteId, odontologoId, formattedDate, hora, motivoConsulta)

        call.enqueue(object : Callback<CitasResponse> {
            override fun onResponse(call: Call<CitasResponse>, response: Response<CitasResponse>) {
                if (response.isSuccessful) {
                    val citasResponse = response.body()
                    if (citasResponse != null && citasResponse.isStatus) {
                        Toast.makeText(this@CitasActivity, "Cita registrada exitosamente", Toast.LENGTH_SHORT).show()
                        finish() // Volver a la vista anterior
                    } else {
                        Log.e("CitasActivity", "Error al registrar la cita: ${response.message()} - ${response.errorBody()?.string()}")
                        Toast.makeText(this@CitasActivity, "Error al registrar la cita: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("CitasActivity", "Error al registrar la cita: ${response.code()} - ${response.message()} - ${response.errorBody()?.string()}")
                    Toast.makeText(this@CitasActivity, "Error al registrar la cita: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<CitasResponse>, t: Throwable) {
                finish() // Volver a la vista anterior
            }
        })
    }

    private fun cargarDatosSpinners() {
        val apiService = RetrofitClient.createService()

        // Cargar pacientes
        val callPacientes = apiService.obtenerPacientes()
        callPacientes.enqueue(object : Callback<PacientesResponse> {
            override fun onResponse(call: Call<PacientesResponse>, response: Response<PacientesResponse>) {
                Log.d("CitasActivity", "Response: ${response.body()}")
                if (response.isSuccessful) {
                    val pacientesResponse = response.body()
                    if (pacientesResponse != null && pacientesResponse.isStatus) {
                        pacientesList = pacientesResponse.data.map { Paciente(it.id, "${it.nombre} ${it.apeCompleto}") }
                        val adapterPaciente = CustomArrayAdapter(this@CitasActivity, pacientesList)
                        adapterPaciente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerPaciente.adapter = adapterPaciente
                    } else {
                        Log.e("CitasActivity", "Error al cargar los pacientes: ${response.message()} - ${response.errorBody()?.string()}")
                        Toast.makeText(this@CitasActivity, "Error al cargar los pacientes: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("CitasActivity", "Error al cargar los pacientes: ${response.code()} - ${response.message()} - ${response.errorBody()?.string()}")
                    Toast.makeText(this@CitasActivity, "Error al cargar los pacientes: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PacientesResponse>, t: Throwable) {
                Log.e("CitasActivity", "Error en la solicitud: ${t.message}", t)
                Toast.makeText(this@CitasActivity, "Error al cargar los pacientes: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // Cargar odontólogos
        val callOdontologos = apiService.obtenerOdontologos()
        callOdontologos.enqueue(object : Callback<OdontologosResponse> {
            override fun onResponse(call: Call<OdontologosResponse>, response: Response<OdontologosResponse>) {
                Log.d("CitasActivity", "Response: ${response.body()}")
                if (response.isSuccessful) {
                    val odontologosResponse = response.body()
                    if (odontologosResponse != null && odontologosResponse.isStatus) {
                        odontologosList = odontologosResponse.data.map { Odontologo(it.id, "${it.nombre} ${it.ape_completo}") }
                        val adapterOdontologo = CustomArrayAdapter(this@CitasActivity, odontologosList)
                        adapterOdontologo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerOdontologo.adapter = adapterOdontologo
                    } else {
                        Log.e("CitasActivity", "Error al cargar los odontólogos: ${response.message()} - ${response.errorBody()?.string()}")
                        Toast.makeText(this@CitasActivity, "Error al cargar los odontólogos: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("CitasActivity", "Error al cargar los odontólogos: ${response.code()} - ${response.message()} - ${response.errorBody()?.string()}")
                    Toast.makeText(this@CitasActivity, "Error al cargar los odontólogos: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OdontologosResponse>, t: Throwable) {
                Log.e("CitasActivity", "Error en la solicitud: ${t.message}", t)
                Toast.makeText(this@CitasActivity, "Error al cargar los odontólogos: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
