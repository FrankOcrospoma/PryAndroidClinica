package com.example.pryandroidclinica

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.pryandroidclinica.databinding.AgregarOdontologoLayoutBinding
import com.example.pryandroidclinica.response.OdontologosResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AgregarOdontologoFragment : Fragment() {
    private var _binding: AgregarOdontologoLayoutBinding? = null
    private val binding get() = _binding!!
    private val serverDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val displayDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AgregarOdontologoLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputFechaNacimientoOdontologo.setOnClickListener { showDatePickerDialog() }
        binding.btnGuardarOdontologo.setOnClickListener { registrarOdontologo() }
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
                binding.inputFechaNacimientoOdontologo.text = displayDateFormat.format(selectedDate.time)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun registrarOdontologo() {

        val nombreUsuario = binding.txtCrearNombreUsuarioOdontologo.text.toString()
        val email = binding.inputEmailOdontologo.text.toString()
        val contrasena = binding.inputPasswordOdontologo.text.toString()
        val estado = 1
        val nombre = binding.inputNombreOdontologo.text.toString()
        val apeCompleto = binding.inputApellidoOdontologo.text.toString()
        val fechaNac = binding.inputFechaNacimientoOdontologo.text.toString()
        val documento = binding.inputDocumentoOdontologo.text.toString()
        val tipoDocumentoId = 1
        val sexo = if (binding.radioGroupSexo.checkedRadioButtonId == R.id.radioMasculino) 1 else 2
        val direccion =  binding.inputDireccionOdontologo.text.toString()
        val telefono = binding.inputNumeroOdontologo.text.toString()

        // Convertir la fecha al formato esperado por el servidor
        val fechaNacFormatted = try {
            serverDateFormat.format(displayDateFormat.parse(fechaNac))
        } catch (e: Exception) {
            Log.e("AgregarOdontologo", "Error al formatear la fecha: ${e.message}")
            Toast.makeText(context, "Error en el formato de la fecha", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = RetrofitClient.createService()
        val call = apiService.agregarOdontologo(
            nombreUsuario, email, contrasena, estado, 1, nombre, apeCompleto, fechaNacFormatted, documento, tipoDocumentoId, sexo, direccion, telefono
        )

        call.enqueue(object : Callback<OdontologosResponse> {
            override fun onResponse(call: Call<OdontologosResponse>, response: Response<OdontologosResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("AgregarOdontologo", "Response JSON: ${response.raw().body()?.string()}")

                    if (responseBody?.isStatus == true) {
                        Toast.makeText(context, "Odontólogo registrado correctamente", Toast.LENGTH_SHORT).show()
                        setFragmentResult("requestKeyOdontologo", Bundle().apply { putString("resultKey", "success") })
                        findNavController().popBackStack()
                    } else {
                        Log.e("AgregarOdontologo", "Error al registrar odontólogo: ${responseBody?.message}")
                        Toast.makeText(context, "Error al registrar odontólogo: ${responseBody?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("AgregarOdontologo", "Error al registrar odontólogo: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Error al registrar odontólogo: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OdontologosResponse>, t: Throwable) {
                Toast.makeText(context, "Odontólogo registrado correctamente", Toast.LENGTH_SHORT).show()
                setFragmentResult("requestKeyOdontologo", Bundle().apply { putString("resultKey", "success") })
                findNavController().popBackStack()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
