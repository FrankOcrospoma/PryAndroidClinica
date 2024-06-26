package com.example.pryandroidclinica

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pryandroidclinica.databinding.CardviewTratamientoBinding
import com.example.pryandroidclinica.response.TratamientoResponse

class TratamientoAdapter(private var tratamientos: List<TratamientoResponse.Tratamiento>) : RecyclerView.Adapter<TratamientoAdapter.TratamientoViewHolder>() {

    fun actualizarLista(nuevaLista: List<TratamientoResponse.Tratamiento>) {
        tratamientos = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TratamientoViewHolder {
        val binding = CardviewTratamientoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TratamientoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TratamientoViewHolder, position: Int) {
        holder.bind(tratamientos[position])
    }

    override fun getItemCount(): Int {
        return tratamientos.size
    }

    inner class TratamientoViewHolder(private val binding: CardviewTratamientoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tratamiento: TratamientoResponse.Tratamiento) {
            binding.nombre.text = tratamiento.nombre
            binding.costo.text = tratamiento.costo?.toString() ?: "Costo no disponible"
        }
    }
}
