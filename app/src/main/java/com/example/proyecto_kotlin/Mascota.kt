package com.example.proyecto_kotlin

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.Locale

data class Mascota(
    val id: Int,
    val nombre: String,
    val especie: String,
    val raza: String,
    val peso: Double,
    val fechaNacimiento: String,
    val fotoUrl: String?
) {
    val edad: Int?
        get() = calcularEdad(fechaNacimiento)

    private fun calcularEdad(fechaNacimiento: String): Int? {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaNac = formato.parse(fechaNacimiento)
        val fechaActual = Calendar.getInstance().time

        if (fechaNac != null) {
            val edad = Calendar.getInstance().apply { time = fechaNac }
            val edadActual = Calendar.getInstance()

            var edadCalculada = edadActual.get(Calendar.YEAR) - edad.get(Calendar.YEAR)

            // Comprobamos si ya pasó el cumpleaños este año o no
            if (edadActual.get(Calendar.MONTH) < edad.get(Calendar.MONTH) ||
                (edadActual.get(Calendar.MONTH) == edad.get(Calendar.MONTH) && edadActual.get(
                    Calendar.DAY_OF_MONTH
                ) < edad.get(Calendar.DAY_OF_MONTH))
            ) {
                edadCalculada--
            }

            return edadCalculada
        }
        return null
    }
}