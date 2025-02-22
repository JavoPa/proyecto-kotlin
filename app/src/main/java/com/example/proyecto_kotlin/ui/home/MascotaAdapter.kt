package com.example.proyecto_kotlin.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_kotlin.Mascota
import com.example.proyecto_kotlin.R

class MascotaDiffCallback(
    private val oldList: List<Mascota>,
    private val newList: List<Mascota>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

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
        val diffCallback = MascotaDiffCallback(mascotas, newMascotas)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mascotas = newMascotas
        diffResult.dispatchUpdatesTo(this)
    }
}