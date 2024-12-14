package com.example.proyecto_kotlin.ui.vacunas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VacunasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Este es el historial de vacunas y antiparasitarios"
    }
    val text: LiveData<String> = _text
}