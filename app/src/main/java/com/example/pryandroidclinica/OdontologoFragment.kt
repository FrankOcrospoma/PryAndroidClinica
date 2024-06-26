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
import com.example.pryandroidclinica.databinding.FragmentOdontologoBinding
import com.example.pryandroidclinica.response.OdontologosResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OdontologosFragment : Fragment() {

    private var _binding: FragmentOdontologoBinding? = null
    private val binding get() = _binding!!
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

        binding.recyclerViewOdondologos.layoutManager = LinearLayoutManager(context)
        odontologoAdapter = OdontologoAdapter(emptyList()) { odontologo ->
            mostrarDialogoConfirmacion(odontologo)
        }
        binding.recyclerViewOdondologos.adapter = odontologoAdapter

        binding.btnAgregarOdontologo.setOnClickListener {
            findNavController().navigate(R.id.action_odontologosFragment_to_agregarOdontologoFragment)
        }

        // Configurar el listener para recibir el resultado del AgregarOdontologoFragment
        parentFragmentManager.setFragmentResultListener("requestKeyOdontologo", this) { _, bundle ->
            val result = bundle.getString("resultKey")
            if (result == "success") {
                // Recargar los datos
                cargarOdontologos()
            }
        }

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

    private fun mostrarDialogoConfirmacion(odontologo: OdontologosResponse.Odontologo) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmar eliminación")
            .setMessage("¿Está seguro de que desea eliminar este odontólogo?")
            .setPositiveButton("Aceptar") { _, _ ->
                eliminarOdontologo(odontologo)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarOdontologo(odontologo: OdontologosResponse.Odontologo) {
        val apiService = RetrofitClient.createService()
        val call = apiService.eliminarOdontologo(odontologo.id)

        call.enqueue(object : Callback<OdontologosResponse> {
            override fun onResponse(call: Call<OdontologosResponse>, response: Response<OdontologosResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    Toast.makeText(context, "Odontólogo eliminado correctamente", Toast.LENGTH_SHORT).show()
                    cargarOdontologos()
                } else {
                    Log.e("OdontologosFragment", "Error al eliminar odontólogo: ${response.code()} - ${response.message()}")
                    Toast.makeText(context, "Error al eliminar odontólogo", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OdontologosResponse>, t: Throwable) {
                Toast.makeText(context, "Odontólogo eliminado correctamente", Toast.LENGTH_SHORT).show()
                cargarOdontologos()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
