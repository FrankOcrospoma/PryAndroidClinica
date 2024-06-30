package com.example.pryandroidclinica

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pryandroidclinica.databinding.CitaItemBinding
import com.example.pryandroidclinica.response.CitasResponse

class CitaAdapter(
    private var citas: List<CitasResponse.Cita>,
    private val onDeleteClick: (CitasResponse.Cita) -> Unit,
    private val onReprogramarClick: (CitasResponse.Cita) -> Unit
) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() {

    fun actualizarLista(nuevaLista: List<CitasResponse.Cita>) {
        citas = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val binding = CitaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        holder.bind(citas[position])
    }

    override fun getItemCount(): Int = citas.size

    inner class CitaViewHolder(private val binding: CitaItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cita: CitasResponse.Cita) {
            binding.txtFechaCitaProgramada.text = cita.fecha
            binding.txtHoraCitaProgramada.text = cita.hora
            binding.txtestado.text = cita.estado
            binding.txtpaciente.text = cita.nombre_paciente
            binding.txtdoc.text = cita.nombre_odontologo
            binding.btnCancelar.setOnClickListener {
                onDeleteClick(cita)
            }
            binding.btnReprogramar.setOnClickListener {
                onReprogramarClick(cita)
            }
        }
    }
}
