package com.example.proyecto_kotlin.ui.vacunas

data class Vacuna(
    val id: Int,
    val nombre: String,
    val fechaAplicacion: String,
    val proximaDosis: String? = null
)