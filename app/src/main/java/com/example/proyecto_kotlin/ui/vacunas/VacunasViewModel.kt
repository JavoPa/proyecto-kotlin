package com.example.proyecto_kotlin.ui.vacunas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VacunasViewModel : ViewModel() {

    private val vacunas = MutableLiveData<MutableList<Vacuna>>(mutableListOf())

    // Método para obtener las vacunas de una mascota específica
    fun obtenerVacunasPorMascota(mascotaId: String): LiveData<List<Vacuna>> {
        return MutableLiveData(vacunas.value?.filter { it.mascotaId == mascotaId })
    }

    // Método para agregar una vacuna
    fun agregarVacuna(vacuna: Vacuna) {
        val listaActual = vacunas.value ?: mutableListOf()
        listaActual.add(vacuna)
        vacunas.value = listaActual
    }
}
