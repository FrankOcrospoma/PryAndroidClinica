package com.example.pryandroidclinica

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pryandroidclinica.databinding.FragmentTratamientoBinding
import com.example.pryandroidclinica.response.TratamientoResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TratamientosFragment : Fragment() {

    private var _binding: FragmentTratamientoBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
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

        recyclerView = binding.recyclerViewTratamientos
        recyclerView.layoutManager = LinearLayoutManager(context)
        tratamientoAdapter = TratamientoAdapter(emptyList()) // Inicializa con lista vacía
        recyclerView.adapter = tratamientoAdapter

        cargarTratamientos()
    }

    private fun cargarTratamientos() {
        val apiService = RetrofitClient.createService()
        val call = apiService.obtenerTratamientos()

        call.enqueue(object : Callback<TratamientoResponse> {
            override fun onResponse(call: Call<TratamientoResponse>, response: Response<TratamientoResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    val tratamientos = response.body()?.getData()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
