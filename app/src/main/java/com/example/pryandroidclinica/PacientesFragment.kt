package com.example.pryandroidclinica

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pryandroidclinica.databinding.FragmentPacienteBinding
import com.example.pryandroidclinica.response.PacientesResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PacientesFragment : Fragment() {

    private var _binding: FragmentPacienteBinding? = null
    private val binding get() = _binding!!
    private lateinit var pacienteAdapter: PacienteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPacienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewPacientes.layoutManager = LinearLayoutManager(context)
        pacienteAdapter = PacienteAdapter(emptyList())
        binding.recyclerViewPacientes.adapter = pacienteAdapter

        cargarPacientes()
    }

    private fun cargarPacientes() {
        val apiService = RetrofitClient.createService()
        val call = apiService.obtenerPacientes()

        call.enqueue(object : Callback<PacientesResponse> {
            override fun onResponse(call: Call<PacientesResponse>, response: Response<PacientesResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    val pacientes = response.body()?.data
                    if (pacientes != null) {
                        pacienteAdapter.actualizarLista(pacientes)
                    } else {
                        Log.e("PacientesFragment", "Error: Lista de pacientes nula")
                    }
                } else {
                    Log.e("PacientesFragment", "Error al obtener pacientes: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PacientesResponse>, t: Throwable) {
                Log.e("PacientesFragment", "Error en la solicitud: ${t.message}", t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
