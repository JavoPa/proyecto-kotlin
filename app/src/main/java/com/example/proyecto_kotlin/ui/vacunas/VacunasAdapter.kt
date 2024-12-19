package com.example.proyecto_kotlin.ui.vacunas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_kotlin.R
import com.example.proyecto_kotlin.ui.vacunas.Vacuna

class VacunasAdapter(private var vacunas: List<Vacuna>) :
    RecyclerView.Adapter<VacunasAdapter.VacunaViewHolder>() {

    class VacunaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textNombre: TextView = view.findViewById(R.id.textVacunaNombre)
        val textFechaAplicacion: TextView = view.findViewById(R.id.textVacunaFechaAplicacion)
        val textProximaDosis: TextView = view.findViewById(R.id.textVacunaProximaDosis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacunaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vacuna, parent, false)
        return VacunaViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacunaViewHolder, position: Int) {
        val vacuna = vacunas[position]
        holder.textNombre.text = vacuna.nombre
        holder.textFechaAplicacion.text = "Fecha de aplicación: ${vacuna.fechaAplicacion}"
        holder.textProximaDosis.text = "Próxima dosis: ${vacuna.proximaDosis ?: "No aplica"}"
    }

    override fun getItemCount(): Int = vacunas.size

    fun actualizarDatos(nuevasVacunas: List<Vacuna>) {
        vacunas = nuevasVacunas
        notifyDataSetChanged()
    }
}
