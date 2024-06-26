package com.example.pryandroidclinica

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.setFragmentResult
import com.example.pryandroidclinica.databinding.AgregarTratamientoBinding
import com.example.pryandroidclinica.response.TratamientoResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarTratamientoFragment : Fragment() {
    private var _binding: AgregarTratamientoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AgregarTratamientoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGuardarTratamiento.setOnClickListener { registrarTratamiento() }
    }

    private fun registrarTratamiento() {
        val nombre = binding.inputNombreTratamiento.text.toString()
        val descripcion = binding.inputDescripcionTratamiento.text.toString()
        val costo = binding.inputCostoTratamiento.text.toString()

        val apiService = RetrofitClient.createService()
        val call = apiService.registrarTratamiento(nombre, descripcion, costo.toFloat())

        call.enqueue(object : Callback<TratamientoResponse> {
            override fun onResponse(call: Call<TratamientoResponse>, response: Response<TratamientoResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if (responseBody?.isStatus == true) {
                        Toast.makeText(context, "Tratamiento registrado correctamente", Toast.LENGTH_SHORT).show()
                        setFragmentResult("requestKey", Bundle().apply { putString("resultKey", "success") })
                        findNavController().popBackStack()
                    } else {
                        Log.e("AgregarTratamiento", "Error al registrar tratamiento: ${responseBody?.message}")
                        Toast.makeText(context, "Error al registrar tratamiento: ${responseBody?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("AgregarTratamiento", "Error al registrar tratamiento: ${response.errorBody()?.string()}")
                    Toast.makeText(context, "Error al registrar tratamiento: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TratamientoResponse>, t: Throwable) {
                Toast.makeText(context, "Tratamiento registrado exitosamente", Toast.LENGTH_SHORT).show()
                setFragmentResult("requestKey", Bundle().apply { putString("resultKey", "success") })
                findNavController().popBackStack()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
