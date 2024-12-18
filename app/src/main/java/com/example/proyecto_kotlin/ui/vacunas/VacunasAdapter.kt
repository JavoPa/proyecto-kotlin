package com.example.proyecto_kotlin.ui.vacunas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_kotlin.R

// Modelo de datos para la vacuna
data class Vacuna(
    val mascotaId: String, // Relación con la mascota
    val tipo: String,
    val fechaAplicacion: String,
    val proximaDosis: String
)

// Adapter para manejar la lista de vacunas
class VacunasAdapter(private var listaVacunas: List<Vacuna>) :
    RecyclerView.Adapter<VacunasAdapter.VacunaViewHolder>() {

    // ViewHolder que representa cada item en la lista
    class VacunaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTipo: TextView = itemView.findViewById(R.id.txtTipoVacuna)
        val txtFechaAplicacion: TextView = itemView.findViewById(R.id.txtFechaAplicacion)
        val txtProximaDosis: TextView = itemView.findViewById(R.id.txtProximaDosis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacunaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vacuna, parent, false)
        return VacunaViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacunaViewHolder, position: Int) {
        val vacuna = listaVacunas[position]
        holder.txtTipo.text = vacuna.tipo
        holder.txtFechaAplicacion.text = vacuna.fechaAplicacion
        holder.txtProximaDosis.text = vacuna.proximaDosis
    }

    override fun getItemCount(): Int = listaVacunas.size

    // Nuevo método para actualizar la lista de vacunas
    fun updateData(nuevaLista: List<Vacuna>) {
        listaVacunas = nuevaLista // Actualiza la lista de datos
        notifyDataSetChanged()    // Notifica al adaptador que los datos han cambiado
    }
}
