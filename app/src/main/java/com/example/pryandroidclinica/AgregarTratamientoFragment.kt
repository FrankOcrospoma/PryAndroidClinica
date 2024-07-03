package com.example.pryandroidclinica

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.pryandroidclinica.databinding.AgregarTratamientoBinding
import com.example.pryandroidclinica.response.TratamientoResponse
import com.example.pryandroidclinica.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarTratamientoFragment : Fragment() {
    private var _binding: AgregarTratamientoBinding? = null
    private val binding get() = _binding!!

    private var tratamientoId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AgregarTratamientoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Verificar si se pasaron argumentos al fragmento
        arguments?.let {
            tratamientoId = it.getInt("id")
            binding.inputNombreTratamiento.setText(it.getString("nombre"))
            binding.inputDescripcionTratamiento.setText(it.getString("descripcion"))
            binding.inputCostoTratamiento.setText(it.getFloat("costo").toString())
            binding.btnGuardarTratamiento.text = "Actualizar Tratamiento"
        }

        binding.btnGuardarTratamiento.setOnClickListener {
            if (tratamientoId == null) {
                registrarTratamiento()
            } else {
                actualizarTratamiento()
            }
        }
    }

    private fun registrarTratamiento() {
        val nombre = binding.inputNombreTratamiento.text.toString()
        val descripcion = binding.inputDescripcionTratamiento.text.toString()
        val costo = binding.inputCostoTratamiento.text.toString().toFloatOrNull() ?: 0f

        val apiService = RetrofitClient.createService()
        val call = apiService.registrarTratamiento(nombre, descripcion, costo)

        call.enqueue(object : Callback<TratamientoResponse> {
            override fun onResponse(call: Call<TratamientoResponse>, response: Response<TratamientoResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    Toast.makeText(context, "Tratamiento registrado correctamente", Toast.LENGTH_SHORT).show()
                    setFragmentResult("requestKey", Bundle().apply { putString("resultKey", "success") })
                    findNavController().popBackStack()
                } else {
                    val errorMessage = response.body()?.message ?: response.errorBody()?.string()
                    Log.e("AgregarTratamiento", "Error al registrar tratamiento: $errorMessage")
                    Toast.makeText(context, "Error al registrar tratamiento: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TratamientoResponse>, t: Throwable) {
                Toast.makeText(context, "Tratamiento registrado correctamente", Toast.LENGTH_SHORT).show()
                setFragmentResult("requestKey", Bundle().apply { putString("resultKey", "success") })
                findNavController().popBackStack()
            }
        })
    }

    private fun actualizarTratamiento() {
        val id = tratamientoId ?: return
        val nombre = binding.inputNombreTratamiento.text.toString()
        val descripcion = binding.inputDescripcionTratamiento.text.toString()
        val costo = binding.inputCostoTratamiento.text.toString().toFloatOrNull() ?: 0f

        val apiService = RetrofitClient.createService()
        val call = apiService.editarTratamiento(nombre, descripcion, costo, id)

        call.enqueue(object : Callback<TratamientoResponse> {
            override fun onResponse(call: Call<TratamientoResponse>, response: Response<TratamientoResponse>) {
                if (response.isSuccessful && response.body()?.isStatus == true) {
                    Toast.makeText(context, "Tratamiento actualizado correctamente", Toast.LENGTH_SHORT).show()
                    setFragmentResult("requestKey", Bundle().apply { putString("resultKey", "success") })
                    findNavController().popBackStack()
                } else {
                    val errorMessage = response.body()?.message ?: response.errorBody()?.string()
                    Log.e("AgregarTratamiento", "Error al actualizar tratamiento: $errorMessage")
                    Toast.makeText(context, "Error al actualizar tratamiento: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TratamientoResponse>, t: Throwable) {
                Toast.makeText(context, "Tratamiento actualziado correctamente", Toast.LENGTH_SHORT).show()
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
