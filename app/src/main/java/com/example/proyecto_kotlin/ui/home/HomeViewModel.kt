package com.example.proyecto_kotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto_kotlin.Mascota

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
                alergias = mutableListOf("Polen", "Ciertos alimentos con gluten"),
                antecedentes = mutableListOf("Dermatitis alérgica tratada en 2022")
            ),
            Mascota(
                id = 2,
                nombre = "Luna",
                especie = "Gato",
                raza = "Siamés",
                fechaNacimiento = "15/06/2019",
                peso = 4.8,
                fotoUrl = null,
                alergias = mutableListOf(),
                antecedentes = mutableListOf("Cirugía de esterilización en 2020")
            ),
            Mascota(
                id = 3,
                nombre = "Rocky",
                especie = "Perro",
                raza = "Bulldog Francés",
                fechaNacimiento = "10/11/2021",
                peso = 11.2,
                fotoUrl = null,
                alergias = mutableListOf("Pollo", "Maíz"),
                antecedentes = mutableListOf()
            ),
            Mascota(
                id = 4,
                nombre = "Coco",
                especie = "Ave",
                raza = "Cacatúa",
                fechaNacimiento = "23/03/2022",
                peso = 0.3,
                fotoUrl = null,
                alergias = mutableListOf(),
                antecedentes = mutableListOf("Fractura de ala derecha en 2023")
            )
        )
        _mascotas.value = mascotas.toMutableList()
    }


    fun agregarMascota(mascota: Mascota) {
        _mascotas.value?.add(mascota)
        _mascotas.value = _mascotas.value
    }
}