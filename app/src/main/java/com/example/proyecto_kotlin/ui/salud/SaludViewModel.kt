package com.example.proyecto_kotlin.ui.salud

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class SaludViewModel : ViewModel() {

    private val _consultas = MutableLiveData<MutableList<Consulta>>(mutableListOf())
    val consultas: LiveData<MutableList<Consulta>> get() = _consultas

    init {
        cargarConsultas()
    }

    private fun cargarConsultas() {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        _consultas.value = listOf(
            Consulta(1, "Control", "Sano", null, dateFormat.parse("14-05-2023")!!, "Veterinario Concepción", 1),
            Consulta(2, "Castración", "Castrado", null, dateFormat.parse("31-10-2024")!!, "Veterinario Concepción", 1),
            Consulta(3, "Desparasitación", "Desparasitado", null, dateFormat.parse("24-12-2023")!!, "Veterinario Concepción", 2)
        ).toMutableList()
    }
}