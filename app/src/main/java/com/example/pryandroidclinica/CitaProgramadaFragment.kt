package com.example.pryandroidclinica

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pryandroidclinica.databinding.CitasProgramadasLayoutBinding
import com.example.pryandroidclinica.response.CitasResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitaProgramadaFragment : Fragment() {

    private var _binding: CitasProgramadasLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CitasProgramadasLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        binding.recyclerViewCitas.layoutManager = LinearLayoutManager(context)

        binding.btnAgregarCita.setOnClickListener {
            findNavController().navigate(R.id.action_citasFragment_to_agregarCitaFragment)
        }

        // Obtener citas programadas
        obtenerCitasProgramadas()
    }

    private fun obtenerCitasProgramadas() {
        val apiService = RetrofitClient.createService()
        val call = apiService.obtenerCitasPaciente()

        call.enqueue(object : Callback<CitasResponse> {
            override fun onResponse(call: Call<CitasResponse>, response: Response<CitasResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    val citas = response.body()?.data
                    if (!citas.isNullOrEmpty()) {
                        mostrarCitas(citas)
                    } else {
                        mostrarMensajeSinCitas()
                    }
                } else {
                    mostrarMensajeError()
                }
            }

            override fun onFailure(call: Call<CitasResponse>, t: Throwable) {
                mostrarMensajeError()
            }
        })
    }

    private fun mostrarCitas(citas: List<CitasResponse.Cita>) {
        val adapter = CitaAdapter(citas, this::reprogramarCita, this::eliminarCita)
        binding.recyclerViewCitas.adapter = adapter
    }

    private fun reprogramarCita(cita: CitasResponse.Cita) {
      //  val action = CitaProgramadaFragmentDirections.actionCitasFragmentToAgregarCitaFragment(cita)
      //  findNavController().navigate(action)
    }

    private fun eliminarCita(cita: CitasResponse.Cita) {
        val apiService = RetrofitClient.createService()
        val call = apiService.cancelarCita(cita.cita_id)

        call.enqueue(object : Callback<CitasResponse> {
            override fun onResponse(call: Call<CitasResponse>, response: Response<CitasResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    Toast.makeText(context, "Cita eliminada exitosamente", Toast.LENGTH_SHORT).show()
                    obtenerCitasProgramadas() // Refrescar la lista de citas
                } else {
                    mostrarMensajeError()
                }
            }

            override fun onFailure(call: Call<CitasResponse>, t: Throwable) {
                mostrarMensajeError()
            }
        })
    }

    private fun mostrarMensajeSinCitas() {
        Toast.makeText(context, "No tienes citas programadas", Toast.LENGTH_SHORT).show()
    }

    private fun mostrarMensajeError() {
        Toast.makeText(context, "Error al cargar las citas", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
