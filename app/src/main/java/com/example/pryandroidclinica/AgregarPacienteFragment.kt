package com.example.pryandroidclinica

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pryandroidclinica.databinding.AgregarPacienteBinding
import com.example.pryandroidclinica.response.PacientesResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AgregarPacienteFragment : Fragment() {
    private var _binding: AgregarPacienteBinding? = null
    private val binding get() = _binding!!
    private val serverDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val displayDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AgregarPacienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtFechaNacimiento.setOnClickListener { showDatePickerDialog() }
        binding.btnRegistrar.setOnClickListener { registrarPaciente() }
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
                binding.txtFechaNacimiento.setText(displayDateFormat.format(selectedDate.time))
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun registrarPaciente() {
        val nombreUsuario = binding.txtCrearNombreUsuario.text.toString()
        val email = binding.txtCrearEmail.text.toString()
        val contrasena = binding.txtCrearContraseA.text.toString()
        val estado = 1
        val nombre = binding.txtCrearNombre.text.toString()
        val apeCompleto = binding.txtCrearApellidos.text.toString()
        val fechaNac = binding.txtFechaNacimiento.text.toString()
        val documento = binding.txtCrearDocumento.text.toString()
        val tipoDocumentoId = 1
        val sexo = if (binding.radioGroupSexo.checkedRadioButtonId == R.id.radioMasculino) 1 else 2
        val direccion = binding.txtDireccion.text.toString()
        val telefono = binding.txtTelefono.text.toString()

        // Convertir la fecha al formato esperado por el servidor
        val fechaNacFormatted = try {
            serverDateFormat.format(displayDateFormat.parse(fechaNac))
        } catch (e: Exception) {
            Log.e("AgregarPaciente", "Error al formatear la fecha: ${e.message}")
            Toast.makeText(context, "Error en el formato de la fecha", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = RetrofitClient.createService()
        val call = apiService.agregarPaciente(
            nombreUsuario, email, contrasena, estado, 1, nombre, apeCompleto, fechaNacFormatted, documento, tipoDocumentoId, sexo, direccion, telefono
        )

        call.enqueue(object : Callback<PacientesResponse> {
            override fun onResponse(call: Call<PacientesResponse>, response: Response<PacientesResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("AgregarPaciente", "Response JSON: ${response.raw().body()?.string()}")

                    if (responseBody?.isStatus == true) {
                        Toast.makeText(context, "Paciente registrado correctamente", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    } else {
                        Log.e("AgregarPaciente", "Error al registrar paciente: ${responseBody?.message}")
                        Toast.makeText(context, "Error al registrar paciente: ${responseBody?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("AgregarPaciente", "Error al registrar paciente: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Error al registrar paciente: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PacientesResponse>, t: Throwable) {
                findNavController().popBackStack()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
