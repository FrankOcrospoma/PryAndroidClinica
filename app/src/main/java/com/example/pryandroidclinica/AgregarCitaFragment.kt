package com.example.pryandroidclinica

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.pryandroidclinica.response.CitasResponse
import com.example.pryandroidclinica.response.OdontologosResponse
import com.example.pryandroidclinica.response.PacientesResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AgregarCitaFragment : Fragment() {
    private lateinit var spinnerPaciente: Spinner
    private lateinit var spinnerOdontologo: Spinner
    private lateinit var txtAgregarFechaCita: TextInputEditText
    private lateinit var txtAgregarHoraCita: TextInputEditText
    private lateinit var txtAgregarMotivoCita: EditText
    private lateinit var btnConfirmar: Button

    private lateinit var pacientesList: List<Paciente>
    private lateinit var odontologosList: List<Odontologo>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.agregar_cita_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerPaciente = view.findViewById(R.id.spinnerPaciente)
        spinnerOdontologo = view.findViewById(R.id.spinnerOdontologo)
        txtAgregarFechaCita = view.findViewById(R.id.txtAgregarFechaCita)
        txtAgregarHoraCita = view.findViewById(R.id.inputHora)
        txtAgregarMotivoCita = view.findViewById(R.id.txtAgregarMotivoCita)
        btnConfirmar = view.findViewById(R.id.crearcita)

        txtAgregarFechaCita.setOnClickListener { showDatePickerDialog() }
        txtAgregarHoraCita.setOnClickListener { showTimePickerDialog() }
        btnConfirmar.setOnClickListener { registrarCita() }


        cargarDatosSpinners()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                txtAgregarFechaCita.setText(dateFormat.format(selectedDate.time))
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
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                txtAgregarHoraCita.setText(selectedTime)
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
                        Toast.makeText(requireContext(), "Cita registrada exitosamente", Toast.LENGTH_SHORT).show()
                        parentFragmentManager.popBackStack()
                    } else {
                        Log.e("AgregarCitaFragment", "Error al registrar la cita: ${response.message()} - ${response.errorBody()?.string()}")
                        Toast.makeText(requireContext(), "Error al registrar la cita: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("AgregarCitaFragment", "Error al registrar la cita: ${response.code()} - ${response.message()} - ${response.errorBody()?.string()}")
                    Toast.makeText(requireContext(), "Error al registrar la cita: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CitasResponse>, t: Throwable) {
                Toast.makeText(context, "Cita registrado correctamente", Toast.LENGTH_SHORT).show()
                setFragmentResult("requestKeyOdontologo", Bundle().apply { putString("resultKey", "success") })
                findNavController().popBackStack()
            }
        })
    }

    private fun cargarDatosSpinners() {
        val apiService = RetrofitClient.createService()

        // Cargar pacientes
        val callPacientes = apiService.obtenerPacientes()
        callPacientes.enqueue(object : Callback<PacientesResponse> {
            override fun onResponse(call: Call<PacientesResponse>, response: Response<PacientesResponse>) {
                if (response.isSuccessful) {
                    val pacientesResponse = response.body()
                    if (pacientesResponse != null && pacientesResponse.isStatus) {
                        pacientesList = pacientesResponse.data.map { Paciente(it.id, "${it.nombre} ${it.apeCompleto}") }
                        val adapterPaciente = CustomArrayAdapter(requireContext(), pacientesList)
                        adapterPaciente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerPaciente.adapter = adapterPaciente
                    } else {
                        Log.e("AgregarCitaFragment", "Error al cargar los pacientes: ${response.message()} - ${response.errorBody()?.string()}")
                        Toast.makeText(requireContext(), "Error al cargar los pacientes: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("AgregarCitaFragment", "Error al cargar los pacientes: ${response.code()} - ${response.message()} - ${response.errorBody()?.string()}")
                    Toast.makeText(requireContext(), "Error al cargar los pacientes: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PacientesResponse>, t: Throwable) {
                Log.e("AgregarCitaFragment", "Error en la solicitud: ${t.message}", t)
                Toast.makeText(requireContext(), "Error al cargar los pacientes: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // Cargar odontólogos
        val callOdontologos = apiService.obtenerOdontologos()
        callOdontologos.enqueue(object : Callback<OdontologosResponse> {
            override fun onResponse(call: Call<OdontologosResponse>, response: Response<OdontologosResponse>) {
                if (response.isSuccessful) {
                    val odontologosResponse = response.body()
                    if (odontologosResponse != null && odontologosResponse.isStatus) {
                        odontologosList = odontologosResponse.data.map { Odontologo(it.id, "${it.nombre} ${it.apeCompleto}") }
                        val adapterOdontologo = CustomArrayAdapter(requireContext(), odontologosList)
                        adapterOdontologo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerOdontologo.adapter = adapterOdontologo
                    } else {
                        Log.e("AgregarCitaFragment", "Error al cargar los odontólogos: ${response.message()} - ${response.errorBody()?.string()}")
                        Toast.makeText(requireContext(), "Error al cargar los odontólogos: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("AgregarCitaFragment", "Error al cargar los odontólogos: ${response.code()} - ${response.message()} - ${response.errorBody()?.string()}")
                    Toast.makeText(requireContext(), "Error al cargar los odontólogos: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OdontologosResponse>, t: Throwable) {
                Log.e("AgregarCitaFragment", "Error en la solicitud: ${t.message}", t)
                Toast.makeText(requireContext(), "Error al cargar los odontólogos: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
