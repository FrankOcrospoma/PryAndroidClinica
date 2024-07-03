package com.example.pryandroidclinica

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pryandroidclinica.databinding.CitasProgramadasLayoutBinding
import com.example.pryandroidclinica.databinding.FragmentCitaProgramadaBinding
import com.example.pryandroidclinica.response.CitasResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitaProgramadaFragment : Fragment() {

    private var _binding: CitasProgramadasLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var citaAdapter: CitaAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CitasProgramadasLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewCitas.layoutManager = LinearLayoutManager(context)
        citaAdapter = CitaAdapter(emptyList(),
            { cita ->
                mostrarDialogoConfirmacion(cita)
            },
            { cita ->
                val bundle = Bundle().apply {
                    putInt("cita_id", cita.cita_id)
                    putString("fecha", cita.fecha)
                    putString("hora", cita.hora)
                    putString("motivo_consulta", cita.motivo_consulta)
                }
                findNavController().navigate(R.id.action_citasFragment_to_reprogramarCitaFragment, bundle)
            }
        )

        binding.recyclerViewCitas.adapter = citaAdapter

        binding.btnAgregarCita.setOnClickListener {
            findNavController().navigate(R.id.action_citasFragment_to_agregarCitaFragment)
        }


        parentFragmentManager.setFragmentResultListener("requestKeyCita", this) { _, bundle ->
            val result = bundle.getString("resultKey")
            if (result == "success") {
                obtenerCitasProgramadas()
            }
        }

        obtenerCitasProgramadas()
    }

    private fun obtenerCitasProgramadas() {
        val apiService = RetrofitClient.createService()
        val call = apiService.obtenerCitasPaciente()

        call.enqueue(object : Callback<CitasResponse> {
            override fun onResponse(call: Call<CitasResponse>, response: Response<CitasResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    val citasResponse = response.body()
                    val citas = citasResponse?.data ?: listOf()
                    val singleCita = citasResponse?.singleData

                    if (citas.isNotEmpty()) {
                        citaAdapter.actualizarLista(citas)
                    } else if (singleCita != null) {
                        citaAdapter.actualizarLista(listOf(singleCita))
                    } else {
                        Log.e("CitaFragment", "Error: Lista de citas nula")
                    }
                } else {
                    Log.e("CitaFragment", "Error al obtener citas: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CitasResponse>, t: Throwable) {
                Log.e("CitaFragment", "Error en la solicitud: ${t.message}", t)
            }
        })
    }

    private fun mostrarDialogoConfirmacion(cita: CitasResponse.Cita) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmar cancelación")
            .setMessage("¿Está seguro de que desea cancelar esta cita?")
            .setPositiveButton("Aceptar") { _, _ ->
                eliminarCita(cita)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarCita(cita: CitasResponse.Cita) {
        val apiService = RetrofitClient.createService()
        val call = apiService.cancelarCita(cita.cita_id)

        call.enqueue(object : Callback<CitasResponse> {
            override fun onResponse(call: Call<CitasResponse>, response: Response<CitasResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    Toast.makeText(context, "Cita eliminada exitosamente", Toast.LENGTH_SHORT).show()
                    setFragmentResult("requestKey", Bundle().apply { putString("resultKey", "success") })
                    obtenerCitasProgramadas()
                } else {
                    Toast.makeText(context, "Error al cancelar la cita", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CitasResponse>, t: Throwable) {
                Toast.makeText(context, "Error al cancelar la cita", Toast.LENGTH_SHORT).show()
                obtenerCitasProgramadas()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
