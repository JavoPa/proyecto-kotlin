package com.example.proyecto_kotlin.ui.vacunas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyecto_kotlin.ui.vacunas.Vacuna

class VacunasViewModel : ViewModel() {

    private val _vacunas = MutableLiveData<List<Vacuna>>()
    val vacunas: LiveData<List<Vacuna>> get() = _vacunas

    init {
        cargarVacunas()
    }

    private fun cargarVacunas() { // TODO : cambiar a datos reales de la base de datos
        _vacunas.value = listOf(
            Vacuna(1, "Vacuna Antirrábica", "2023-01-10", "2024-01-10"),
            Vacuna(2, "Vacuna Triple Felina", "2023-02-15", "2024-02-15"),
            Vacuna(3, "Vacuna Leptospira", "2023-03-20", "2024-03-20")
        )
    }
}
