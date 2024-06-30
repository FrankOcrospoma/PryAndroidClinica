package com.example.pryandroidclinica

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pryandroidclinica.databinding.ReprogramarCitaBinding
import com.example.pryandroidclinica.response.CitasResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ReprogramarCitaFragment : Fragment() {

    private var _binding: ReprogramarCitaBinding? = null
    private val binding get() = _binding!!

    private lateinit var cita: CitasResponse.Cita

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReprogramarCitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cita = arguments?.getParcelable("cita")!!

        binding.txtAgregarFechaCita.setText(cita.fecha)
        binding.txtAgregarHoraCita.setText(cita.hora)

        binding.txtAgregarFechaCita.setOnClickListener { showDatePickerDialog() }
        binding.txtAgregarHoraCita.setOnClickListener { showTimePickerDialog() }
        binding.btnConfirmar.setOnClickListener { reprogramarCita() }
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
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                binding.txtAgregarFechaCita.setText(dateFormat.format(selectedDate.time))
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
                binding.txtAgregarHoraCita.setText(selectedTime)
            },
            hour, minute, true
        )
        timePickerDialog.show()
    }

    private fun reprogramarCita() {
        val nuevaFecha = binding.txtAgregarFechaCita.text.toString()
        val nuevaHora = binding.txtAgregarHoraCita.text.toString()
        val motivo = binding.txtAgregarMotivoCita.text.toString()

        val apiService = RetrofitClient.createService()
        val call = apiService.reprogramarCita(cita.cita_id, nuevaFecha, nuevaHora)

        call.enqueue(object : Callback<CitasResponse> {
            override fun onResponse(call: Call<CitasResponse>, response: Response<CitasResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    Toast.makeText(context, "Cita reprogramada exitosamente", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Error al reprogramar la cita", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CitasResponse>, t: Throwable) {
                Toast.makeText(context, "Error en la solicitud", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
