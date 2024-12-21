package com.example.proyecto_kotlin.ui.vacunas

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.proyecto_kotlin.R

class AgregarVacunaDialog(private val onVacunaAgregada: (Vacuna) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_agregar_vacuna, null)
        dialog.setContentView(view)

        val nombreEditText = view.findViewById<EditText>(R.id.etNombreVacuna)
        val fechaAplicacionEditText = view.findViewById<EditText>(R.id.etFechaAplicacion)
        val proximaDosisEditText = view.findViewById<EditText>(R.id.etProximaDosis)
        val agregarButton = view.findViewById<Button>(R.id.btnAgregarVacuna)

        agregarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()
            val fechaAplicacion = fechaAplicacionEditText.text.toString().trim()
            val proximaDosis = proximaDosisEditText.text.toString().trim()

            if (nombre.isEmpty() || fechaAplicacion.isEmpty() || proximaDosis.isEmpty()) {
                Toast.makeText(context, "Por favor, completa todos los campos correctamente.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevaVacuna = Vacuna(
                id = System.currentTimeMillis().toInt(),
                nombre = nombre,
                fechaAplicacion = fechaAplicacion,
                proximaDosis = proximaDosis
            )

            onVacunaAgregada(nuevaVacuna)
            dismiss()
        }

        return dialog
    }
}
