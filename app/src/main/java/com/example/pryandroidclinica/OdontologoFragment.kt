package com.example.pryandroidclinica

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pryandroidclinica.databinding.FragmentOdontologoBinding
import com.example.pryandroidclinica.response.OdontologosResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OdontologosFragment : Fragment() {

    private var _binding: FragmentOdontologoBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var odontologoAdapter: OdontologoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOdontologoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerViewOdondologos
        recyclerView.layoutManager = LinearLayoutManager(context)
        odontologoAdapter = OdontologoAdapter(emptyList())
        binding.recyclerViewOdondologos.adapter = odontologoAdapter

        cargarOdontologos()
    }

    private fun cargarOdontologos() {
        val apiService = RetrofitClient.createService()
        val call = apiService.listaOdontologos()

        call.enqueue(object : Callback<OdontologosResponse> {
            override fun onResponse(call: Call<OdontologosResponse>, response: Response<OdontologosResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    val odontologos = response.body()?.data
                    if (odontologos != null) {
                        odontologoAdapter.actualizarLista(odontologos)
                    } else {
                        Log.e("OdontologosFragment", "Error: Lista de odontólogos nula")
                    }
                } else {
                    Log.e("OdontologosFragment", "Error al obtener odontólogos: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<OdontologosResponse>, t: Throwable) {
                Log.e("OdontologosFragment", "Error en la solicitud: ${t.message}", t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
