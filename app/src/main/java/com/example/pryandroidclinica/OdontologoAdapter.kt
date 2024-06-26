package com.example.pryandroidclinica

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pryandroidclinica.databinding.CardviewOdontologoBinding
import com.example.pryandroidclinica.response.OdontologosResponse

class OdontologoAdapter(private var odontologos: List<OdontologosResponse.Odontologo>) : RecyclerView.Adapter<OdontologoAdapter.OdontologoViewHolder>() {

    fun actualizarLista(nuevaLista: List<OdontologosResponse.Odontologo>) {
        odontologos = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OdontologoViewHolder {
        val binding = CardviewOdontologoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OdontologoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OdontologoViewHolder, position: Int) {
        holder.bind(odontologos[position])
    }

    override fun getItemCount(): Int {
        return odontologos.size
    }

    inner class OdontologoViewHolder(private val binding: CardviewOdontologoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(odontologo: OdontologosResponse.Odontologo) {
            binding.nombreTextView.text = odontologo.nombre
            binding.txtEstado.text = odontologo.especialidad
        }
    }
}
