package com.example.proyecto_kotlin.ui.vacunas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VacunasViewModel : ViewModel() {

    // LiveData para la lista de vacunas
    private val _listaVacunas = MutableLiveData<MutableList<Vacuna>>(mutableListOf())
    val listaVacunas: LiveData<MutableList<Vacuna>> get() = _listaVacunas

    // Método para agregar una vacuna
    fun agregarVacuna(nuevaVacuna: Vacuna) {
        val listaActual = _listaVacunas.value ?: mutableListOf()
        listaActual.add(nuevaVacuna)
        _listaVacunas.value = listaActual
    }
}
