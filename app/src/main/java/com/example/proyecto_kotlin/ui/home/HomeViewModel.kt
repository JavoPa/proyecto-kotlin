package com.example.proyecto_kotlin.ui.home

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto_kotlin.Consulta
import com.example.proyecto_kotlin.Mascota
import java.util.Locale

class HomeViewModel : ViewModel() {

    private val _mascotas = MutableLiveData<MutableList<Mascota>>(mutableListOf())
    val mascotas: LiveData<MutableList<Mascota>> get() = _mascotas

    init {
        cargarMascotas()
    }

    private fun cargarMascotas() {
        val mascotas = listOf(
            Mascota(
                id = 1,
                nombre = "Max",
                especie = "Perro",
                raza = "Golden Retriever",
                fechaNacimiento = "01/01/2020",
                peso = 30.5,
                fotoUrl = null,
                alergias = listOf("Polen", "Ciertos alimentos con gluten"),
                antecedentes = listOf("Dermatitis alérgica tratada en 2022"),
                consultas = listOf(
                    Consulta(1, "Control", "Sano", null, Calendar.getInstance().time, "Veterinario Concepción"),
                    Consulta(2, "Castración", "Castrado", null, Calendar.getInstance().time, "Veterinario Concepción"),
                    Consulta(3, "Desparasitación", "Desparasitado", null, Calendar.getInstance().time, "Veterinario Concepción")
                )
            ),
            Mascota(
                id = 2,
                nombre = "Luna",
                especie = "Gato",
                raza = "Siamés",
                fechaNacimiento = "15/06/2019",
                peso = 4.8,
                fotoUrl = null,
                alergias = emptyList(),
                antecedentes = listOf("Cirugía de esterilización en 2020"),
                consultas = emptyList()
            ),
            Mascota(
                id = 3,
                nombre = "Rocky",
                especie = "Perro",
                raza = "Bulldog Francés",
                fechaNacimiento = "10/11/2021",
                peso = 11.2,
                fotoUrl = null,
                alergias = listOf("Pollo", "Maíz"),
                antecedentes = emptyList(),
                consultas = emptyList()
            ),
            Mascota(
                id = 4,
                nombre = "Coco",
                especie = "Ave",
                raza = "Cacatúa",
                fechaNacimiento = "23/03/2022",
                peso = 0.3,
                fotoUrl = null,
                alergias = emptyList(),
                antecedentes = listOf("Fractura de ala derecha en 2023"),
                consultas = emptyList()
            )
        )
        _mascotas.value = mascotas.toMutableList()
    }


    fun agregarMascota(mascota: Mascota) {
        _mascotas.value?.add(mascota)
        _mascotas.value = _mascotas.value
    }
}