package com.example.proyecto_kotlin.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.proyecto_kotlin.Mascota
import com.example.proyecto_kotlin.R

class AgregarMascotaDialog(private val onMascotaAgregada: (Mascota) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_agregar_mascota, null)
        dialog.setContentView(view)

        val nombreEditText = view.findViewById<EditText>(R.id.etNombreMascota)
        val especieEditText = view.findViewById<EditText>(R.id.etEspecieMascota)
        val razaEditText = view.findViewById<EditText>(R.id.etRazaMascota)
        val fechaEditText = view.findViewById<EditText>(R.id.etFechaNacimientoMascota)
        val agregarButton = view.findViewById<Button>(R.id.btnAgregarMascota)
        val pesoEditText = view.findViewById<EditText>(R.id.etPesoMascota)

        agregarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val especie = especieEditText.text.toString()
            val raza = razaEditText.text.toString()
            val fechaNacimiento = fechaEditText.text.toString()
            val pesoString = pesoEditText.text.toString()
            val peso = pesoString.toDouble()


            if (nombre.isNotEmpty() && especie.isNotEmpty() && raza.isNotEmpty() && fechaNacimiento.isNotEmpty()) {
                val nuevaMascota = Mascota(
                    id = System.currentTimeMillis().toInt(),
                    nombre = nombre,
                    especie = especie,
                    raza = raza,
                    fechaNacimiento = fechaNacimiento,
                    peso = peso,
                    fotoUrl = null
                )
                onMascotaAgregada(nuevaMascota)
                dismiss()
            }else {
                Toast.makeText(context, "Por favor, completa todos los campos correctamente.", Toast.LENGTH_SHORT).show()
            }
        }

        return dialog
    }
}