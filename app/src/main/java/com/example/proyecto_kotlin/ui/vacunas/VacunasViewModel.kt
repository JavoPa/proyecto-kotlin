package com.example.proyecto_kotlin.ui.vacunas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto_kotlin.models.Vacuna

class VacunasViewModel : ViewModel() {

    private val _vacunas = MutableLiveData<MutableList<Vacuna>>(mutableListOf())
    val vacunas: LiveData<MutableList<Vacuna>> get() = _vacunas

    init {
        cargarVacunas()
    }

    // Agregar vacuna - correctamente actualizado
    fun agregarVacuna(vacuna: Vacuna) {
        // Crear una nueva lista mutable con la vacuna agregada
        val listaActualizada = _vacunas.value.orEmpty().toMutableList()
        listaActualizada.add(vacuna)
        // Actualizar el valor de _vacunas
        _vacunas.value = listaActualizada
    }

    // Editar vacuna
    fun editarVacuna(vacunaEditada: Vacuna) {
        val listaActualizada = _vacunas.value.orEmpty().map {
            if (it.id == vacunaEditada.id) vacunaEditada else it
        }
        _vacunas.value = listaActualizada.toMutableList() // Convertir de nuevo a MutableList
    }

    // Eliminar vacuna
    fun eliminarVacuna(vacuna: Vacuna) {
        val listaActualizada = _vacunas.value.orEmpty().filter { it.id != vacuna.id }
        _vacunas.value = listaActualizada.toMutableList() // Convertir a MutableList
    }

    // Cargar vacunas iniciales
    private fun cargarVacunas() {
        _vacunas.value = listOf(
            Vacuna(1, "Vacuna Antirrábica", "2023-01-10", "2024-01-10"),
            Vacuna(2, "Vacuna Triple Felina", "2023-02-15", "2024-02-15"),
            Vacuna(3, "Vacuna Leptospira", "2023-03-20", "2024-03-20")
        ).toMutableList()
    }
}
