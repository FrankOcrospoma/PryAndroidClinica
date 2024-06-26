package com.example.pryandroidclinica

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        pacienteAdapter = PacienteAdapter(emptyList()) { paciente ->
            mostrarDialogoConfirmacion(paciente)
        }
        binding.recyclerViewPacientes.adapter = pacienteAdapter

        binding.btnAgregarPaciente.setOnClickListener {
            findNavController().navigate(R.id.action_pacientesFragment_to_agregarPacienteFragment)
        }

        // Configurar el listener para recibir el resultado del AgregarPacienteFragment
        parentFragmentManager.setFragmentResultListener("requestKeyPaciente", this) { _, bundle ->
            val result = bundle.getString("resultKey")
            if (result == "success") {
                // Recargar los datos
                cargarPacientes()
            }
        }

        cargarPacientes()
    }

    private fun cargarPacientes() {
        val apiService = RetrofitClient.createService()
        val call = apiService.obtenerPacientes()

        call.enqueue(object : Callback<PacientesResponse> {
            override fun onResponse(call: Call<PacientesResponse>, response: Response<PacientesResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    val pacientesResponse = response.body()
                    val pacientes = pacientesResponse?.data ?: listOf()
                    val singlePaciente = pacientesResponse?.singleData

                    if (pacientes.isNotEmpty()) {
                        pacienteAdapter.actualizarLista(pacientes)
                    } else if (singlePaciente != null) {
                        pacienteAdapter.actualizarLista(listOf(singlePaciente))
                    } else {
                        Log.e("PacientesFragment", "Error: Lista de pacientes nula")
                    }
                } else {
                    Log.e("PacientesFragment", "Error al obtener paciente: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PacientesResponse>, t: Throwable) {
                Log.e("PacientesFragment", "Error en la solicitud: ${t.message}", t)
            }
        })
    }

    private fun mostrarDialogoConfirmacion(paciente: PacientesResponse.Paciente) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmar eliminación")
            .setMessage("¿Está seguro de que desea eliminar este paciente?")
            .setPositiveButton("Aceptar") { _, _ ->
                eliminarPaciente(paciente)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarPaciente(paciente: PacientesResponse.Paciente) {
        val apiService = RetrofitClient.createService()
        val call = apiService.eliminarPaciente(paciente.id)

        call.enqueue(object : Callback<PacientesResponse> {
            override fun onResponse(call: Call<PacientesResponse>, response: Response<PacientesResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    Toast.makeText(context, "Paciente eliminado correctamente", Toast.LENGTH_SHORT).show()
                    cargarPacientes()
                } else {
                    Log.e("PacientesFragment", "Error al eliminar paciente: ${response.code()} - ${response.message()}")
                    Toast.makeText(context, "Error al eliminar paciente", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PacientesResponse>, t: Throwable) {
                Toast.makeText(context, "Paciente eliminado correctamente", Toast.LENGTH_SHORT).show()
                cargarPacientes()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
