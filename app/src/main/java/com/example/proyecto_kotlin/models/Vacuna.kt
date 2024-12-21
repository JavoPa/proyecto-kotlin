package com.example.proyecto_kotlin.models

data class Vacuna(
    val id: Int,
    val nombre: String,
    val fechaAplicacion: String,
    val proximaDosis: String? = null
)