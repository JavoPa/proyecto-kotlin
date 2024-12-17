package com.example.proyecto_kotlin.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_kotlin.Mascota
import com.example.proyecto_kotlin.R

class MascotaAdapter(private var mascotas: List<Mascota>, private val onItemClick: (Mascota) -> Unit) :
    RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder>() {

    class MascotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.textViewNombre)
        val detallesTextView: TextView = itemView.findViewById(R.id.textViewDetalles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mascota, parent, false)
        return MascotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MascotaViewHolder, position: Int) {
        val mascota = mascotas[position]
        holder.nombreTextView.text = mascota.nombre
        holder.detallesTextView.text = "${mascota.especie} - ${mascota.raza}"

        holder.itemView.setOnClickListener {
            onItemClick(mascota)
        }
    }

    override fun getItemCount(): Int = mascotas.size

    fun updateData(newMascotas: List<Mascota>) {
        mascotas = newMascotas
        notifyDataSetChanged()
    }
}