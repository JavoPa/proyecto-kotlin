package com.example.proyecto_kotlin.ui.home

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
                peso = 10.4,
                fotoUrl = null
            ),
            Mascota(
                id = 2,
                nombre = "Luna",
                especie = "Gato",
                raza = "Siames",
                fechaNacimiento = "15/06/2019",
                peso = 10.4,
                fotoUrl = null
            ),
            Mascota(
                id = 3,
                nombre = "Rocky",
                especie = "Perro",
                raza = "Bulldog Francés",
                fechaNacimiento = "10/11/2021",
                peso = 10.4,
                fotoUrl = null
            ),
            Mascota(
                id = 4,
                nombre = "Coco",
                especie = "Ave",
                raza = "Cacatúa",
                fechaNacimiento = "23/03/2022",
                peso = 10.4,
                fotoUrl = null
            )
        )
        _mascotas.value = mascotas.toMutableList()
    }


    fun agregarMascota(mascota: Mascota) {
        _mascotas.value?.add(mascota)
        _mascotas.value = _mascotas.value
    }
}