package com.example.proyecto_kotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SharedMascotaViewModel(application: Application) : AndroidViewModel(application) {

    private val mascotaDao: MascotaDao = (application as MyApplication).database.mascotaDao()
    private val _mascotaSeleccionada = MutableLiveData<Mascota?>()
    val mascotaSeleccionada: LiveData<Mascota?> get() = _mascotaSeleccionada

    fun seleccionarMascota(mascota: Mascota) {
        _mascotaSeleccionada.value = mascota
    }

    fun actualizarMascota(mascota: Mascota) {
        viewModelScope.launch {
            mascotaDao.update(mascota)
            _mascotaSeleccionada.postValue(mascota)
        }
    }
}