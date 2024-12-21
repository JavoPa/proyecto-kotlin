package com.example.proyecto_kotlin.ui.salud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_kotlin.models.Consulta
import com.example.proyecto_kotlin.R

class SaludAdapter(private val consultas: List<Consulta>) :
    RecyclerView.Adapter<SaludAdapter.SaludViewHolder>() {

    class SaludViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val motivoTextView: TextView = itemView.findViewById(R.id.textViewMotivo)
        val diagnosticoTextView: TextView = itemView.findViewById(R.id.textViewDiagnostico)
        val fechaTextView: TextView = itemView.findViewById(R.id.textViewFecha)
        val veterinarioTextView: TextView = itemView.findViewById(R.id.textViewVeterinario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaludViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_salud, parent, false)
        return SaludViewHolder(view)
    }

    override fun onBindViewHolder(holder: SaludViewHolder, position: Int) {
        val consulta = consultas[position]
        holder.motivoTextView.text = consulta.motivo
        holder.diagnosticoTextView.text = consulta.diagnostico
        holder.fechaTextView.text = consulta.fecha.toString()
        holder.veterinarioTextView.text = consulta.veterinario
    }

    override fun getItemCount(): Int = consultas.size
}
