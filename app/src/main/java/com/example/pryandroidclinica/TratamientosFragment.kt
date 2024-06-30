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
import com.example.pryandroidclinica.databinding.FragmentTratamientoBinding
import com.example.pryandroidclinica.response.TratamientoResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TratamientosFragment : Fragment() {

    private var _binding: FragmentTratamientoBinding? = null
    private val binding get() = _binding!!
    private lateinit var tratamientoAdapter: TratamientoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTratamientoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewTratamientos.layoutManager = LinearLayoutManager(context)
        tratamientoAdapter = TratamientoAdapter(
            emptyList(),
            onEliminarClick = { tratamiento ->
                mostrarDialogoConfirmacion(tratamiento)
            },
            onEditClick = { tratamiento ->
                editarTratamiento(tratamiento)
            }
        )
        binding.recyclerViewTratamientos.adapter = tratamientoAdapter
        binding.btnAgregarTratamiento.setOnClickListener {
            findNavController().navigate(R.id.action_nav_manage_treatments_to_nav_agregar_tratamiento)
        }

        cargarTratamientos()
    }

    private fun cargarTratamientos() {
        val apiService = RetrofitClient.createService()
        val call = apiService.obtenerTratamientos()

        call.enqueue(object : Callback<TratamientoResponse> {
            override fun onResponse(call: Call<TratamientoResponse>, response: Response<TratamientoResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    val tratamientos = response.body()?.data
                    if (tratamientos != null) {
                        tratamientoAdapter.actualizarLista(tratamientos)
                    } else {
                        Log.e("TratamientosFragment", "Error: Lista de tratamientos nula")
                    }
                } else {
                    Log.e("TratamientosFragment", "Error al obtener tratamientos: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TratamientoResponse>, t: Throwable) {
                Log.e("TratamientosFragment", "Error en la solicitud: ${t.message}", t)
            }
        })
    }

    private fun mostrarDialogoConfirmacion(tratamiento: TratamientoResponse.Tratamiento) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmar eliminación")
            .setMessage("¿Está seguro de que desea eliminar este tratamiento?")
            .setPositiveButton("Aceptar") { _, _ ->
                eliminarTratamiento(tratamiento)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarTratamiento(tratamiento: TratamientoResponse.Tratamiento) {
        val apiService = RetrofitClient.createService()
        val call = apiService.eliminarTratamiento(tratamiento.id)

        call.enqueue(object : Callback<TratamientoResponse> {
            override fun onResponse(call: Call<TratamientoResponse>, response: Response<TratamientoResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    Toast.makeText(context, "Tratamiento eliminado correctamente", Toast.LENGTH_SHORT).show()
                    cargarTratamientos()
                } else {
                    Log.e("TratamientosFragment", "Error al eliminar tratamiento: ${response.code()} - ${response.message()}")
                    Toast.makeText(context, "Este tratamiento está presente en alguna cita", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TratamientoResponse>, t: Throwable) {
                Toast.makeText(context, "Tratamiento eliminado exitosamente", Toast.LENGTH_SHORT).show()
                setFragmentResult("requestKey", Bundle().apply { putString("resultKey", "success") })
                cargarTratamientos()
            }
        })
    }

    private fun editarTratamiento(tratamiento: TratamientoResponse.Tratamiento) {
        val bundle = Bundle().apply {
            putInt("id", tratamiento.id)
            putString("nombre", tratamiento.nombre)
            putString("descripcion", tratamiento.descripcion)
            putFloat("costo", tratamiento.costo ?: 0f)
        }
        findNavController().navigate(R.id.action_nav_manage_treatments_to_nav_agregar_tratamiento, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
