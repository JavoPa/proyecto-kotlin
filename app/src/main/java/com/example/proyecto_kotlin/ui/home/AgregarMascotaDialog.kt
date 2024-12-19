package com.example.proyecto_kotlin.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.proyecto_kotlin.Mascota
import com.example.proyecto_kotlin.R

class AgregarMascotaDialog(private val onMascotaAgregada: (Mascota) -> Unit) : DialogFragment() {

    private val especiesYrazas = mapOf(
        "Perro" to listOf("Labrador", "Pastor Alemán", "Poodle", "Bulldog"),
        "Gato" to listOf("Siamés", "Persa", "Maine Coon", "Bengala"),
        "Ave" to listOf("Canario", "Loro", "Periquito"),
        "Otro" to listOf("Conejo", "Hámster", "Tortuga")
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_agregar_mascota, null)
        dialog.setContentView(view)

        val nombreEditText = view.findViewById<EditText>(R.id.etNombreMascota)
        val fechaEditText = view.findViewById<EditText>(R.id.etFechaNacimientoMascota)
        val agregarButton = view.findViewById<Button>(R.id.btnAgregarMascota)
        val especieSpinner = view.findViewById<Spinner>(R.id.spinnerEspecieMascota)
        val razaSpinner = view.findViewById<Spinner>(R.id.spinnerRazaMascota)

        val especies = especiesYrazas.keys.toList()
        val especieAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, especies)
        especieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        especieSpinner.adapter = especieAdapter

        especieSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val especieSeleccionada = especies[position]
                val razas = especiesYrazas[especieSeleccionada] ?: emptyList()

                val razaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, razas)
                razaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                razaSpinner.adapter = razaAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        agregarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()
            val especie = especieSpinner.selectedItem.toString().trim()
            val raza = razaSpinner.selectedItem.toString().trim()
            val fechaNacimiento = fechaEditText.text.toString().trim()

            if (nombre.isEmpty() || especie.isEmpty() || raza.isEmpty() || fechaNacimiento.isEmpty()) {
                Toast.makeText(context, "Por favor, completa todos los campos correctamente.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
package com.example.proyecto_kotlin.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.proyecto_kotlin.Mascota
import com.example.proyecto_kotlin.R

class AgregarMascotaDialog(private val onMascotaAgregada: (Mascota) -> Unit) : DialogFragment() {

    private val especiesYrazas = mapOf(
        "Perro" to listOf("Labrador", "Pastor Alemán", "Poodle", "Bulldog"),
        "Gato" to listOf("Siamés", "Persa", "Maine Coon", "Bengala"),
        "Ave" to listOf("Canario", "Loro", "Periquito"),
        "Otro" to listOf("Conejo", "Hámster", "Tortuga")
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_agregar_mascota, null)
        dialog.setContentView(view)

        val nombreEditText = view.findViewById<EditText>(R.id.etNombreMascota)
        val fechaEditText = view.findViewById<EditText>(R.id.etFechaNacimientoMascota)
        val agregarButton = view.findViewById<Button>(R.id.btnAgregarMascota)
        val especieSpinner = view.findViewById<Spinner>(R.id.spinnerEspecieMascota)
        val razaSpinner = view.findViewById<Spinner>(R.id.spinnerRazaMascota)

        val especies = especiesYrazas.keys.toList()
        val especieAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, especies)
        especieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        especieSpinner.adapter = especieAdapter

        especieSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val especieSeleccionada = especies[position]
                val razas = especiesYrazas[especieSeleccionada] ?: emptyList()

                val razaAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, razas)
                razaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                razaSpinner.adapter = razaAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        agregarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()
            val especie = especieSpinner.selectedItem.toString().trim()
            val raza = razaSpinner.selectedItem.toString().trim()
            val fechaNacimiento = fechaEditText.text.toString().trim()

            if (nombre.isEmpty() || especie.isEmpty() || raza.isEmpty() || fechaNacimiento.isEmpty()) {
                Toast.makeText(context, "Por favor, completa todos los campos correctamente.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nuevaMascota = Mascota(
                id = System.currentTimeMillis().toInt(),
                nombre = nombre,
                especie = especie,
                raza = raza,
                fechaNacimiento = fechaNacimiento,
                peso = null,
                alergias = mutableListOf(),
                antecedentes = mutableListOf(),
                fotoUrl = null
            )

            onMascotaAgregada(nuevaMascota)
            dismiss()
        }

        return dialog
    }
}
            val nuevaMascota = Mascota(
                id = System.currentTimeMillis().toInt(),
                nombre = nombre,
                especie = especie,
                raza = raza,
                fechaNacimiento = fechaNacimiento,
                peso = null,
                alergias = mutableListOf(),
                antecedentes = mutableListOf(),
                fotoUrl = null,
                consultas = mutableListOf()
            )

            onMascotaAgregada(nuevaMascota)
            dismiss()
        }

        return dialog
    }
}