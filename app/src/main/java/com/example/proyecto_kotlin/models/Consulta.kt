package com.example.proyecto_kotlin.models

import java.util.Date

data class Consulta(
    val id: Int,
    val motivo: String,
    val diagnostico: String,
    val indicaciones: String?,
    val fecha: Date,
    val veterinario: String
) {
    private fun obtenerDiagnostico() = diagnostico
}