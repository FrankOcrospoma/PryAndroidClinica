package com.example.pryandroidclinica;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pryandroidclinica.databinding.CardviewPacienteBinding;
import com.example.pryandroidclinica.response.PacientesResponse;

class PacienteAdapter(
    private var pacientes: List<PacientesResponse.Paciente>,
    private val onDeleteClick: (PacientesResponse.Paciente) -> Unit
) : RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder>() {

    fun actualizarLista(nuevaLista: List<PacientesResponse.Paciente>) {
        pacientes = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacienteViewHolder {
        val binding = CardviewPacienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PacienteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PacienteViewHolder, position: Int) {
        holder.bind(pacientes[position])
    }

    override fun getItemCount(): Int {
        return pacientes.size
    }

    inner class PacienteViewHolder(private val binding: CardviewPacienteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(paciente: PacientesResponse.Paciente) {
            binding.nombrePacienteTextView.text = paciente.nombre
            binding.txtPacienteDNI.text = paciente.documento
            binding.txtPacienteEmail.text = paciente.email
            binding.btnEliminar.setOnClickListener {
                onDeleteClick(paciente)
            }
        }
    }
}
