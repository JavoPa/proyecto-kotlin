package com.example.proyecto_kotlin.ui.vacunas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.proyecto_kotlin.MyApplication
import kotlinx.coroutines.launch



class VacunasViewModel(application: Application) : AndroidViewModel(application) {

    private val vacunaDao: VacunaDao = (application as MyApplication).database.vacunaDao()

    val vacunas: LiveData<List<Vacuna>> = vacunaDao.getAll()

    // Agregar vacuna
    fun agregarVacuna(vacuna: Vacuna) {
        viewModelScope.launch {
            vacunaDao.insert(vacuna)
        }
    }

    // Editar vacuna
    fun editarVacuna(vacunaEditada: Vacuna) {
        viewModelScope.launch {
            vacunaDao.update(vacunaEditada)
        }
    }

    // Eliminar vacuna
    fun eliminarVacuna(vacuna: Vacuna) {
        viewModelScope.launch {
            vacunaDao.delete(vacuna)
        }
    }
}