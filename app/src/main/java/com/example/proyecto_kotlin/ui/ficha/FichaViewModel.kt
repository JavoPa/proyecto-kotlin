package com.example.proyecto_kotlin.ui.ficha

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FichaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Esta es la Ficha Medica"
    }
    val text: LiveData<String> = _text
}