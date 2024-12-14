package com.example.proyecto_kotlin.ui.salud

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SaludViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Este es el historial de salud y estadísticas"
    }
    val text: LiveData<String> = _text
}