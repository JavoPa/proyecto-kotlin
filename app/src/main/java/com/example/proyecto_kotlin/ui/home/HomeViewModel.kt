package com.example.proyecto_kotlin.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.proyecto_kotlin.MyApplication
import com.example.proyecto_kotlin.Mascota
import com.example.proyecto_kotlin.MascotaDao
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val mascotaDao: MascotaDao = (application as MyApplication).database.mascotaDao()
    private val sharedPreferences = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    val mascotas: LiveData<List<Mascota>> = mascotaDao.getAll()

    init {
        if (!datosDeEjemploCargados()) {
            cargarMascotasDeEjemplo()
        }
    }

    // Verificar si los datos de ejemplo ya han sido cargados
    private fun datosDeEjemploCargados(): Boolean {
        return sharedPreferences.getBoolean("datos_de_ejemplo_cargados", false)
    }

    // Marcar que los datos de ejemplo han sido cargados
    private fun marcarDatosDeEjemploCargados() {
        sharedPreferences.edit().putBoolean("datos_de_ejemplo_cargados", true).apply()
    }

    // Cargar datos de ejemplo
    private fun cargarMascotasDeEjemplo() {
        viewModelScope.launch {
            val mascotasDeEjemplo = listOf(
                Mascota(
                    nombre = "Max",
                    especie = "Perro",
                    raza = "Golden Retriever",
                    fechaNacimiento = "01/01/2020",
                    peso = 30.5,
                    fotoUrl = null,
                    alergias = mutableListOf("Polen", "Ciertos alimentos con gluten"),
                    antecedentes = mutableListOf("Dermatitis alérgica tratada en 2022")
                ),
                Mascota(
                    nombre = "Luna",
                    especie = "Gato",
                    raza = "Siamés",
                    fechaNacimiento = "15/06/2019",
                    peso = 4.8,
                    fotoUrl = null,
                    alergias = mutableListOf(),
                    antecedentes = mutableListOf("Cirugía de esterilización en 2020")
                ),
                Mascota(
                    nombre = "Rocky",
                    especie = "Perro",
                    raza = "Bulldog Francés",
                    fechaNacimiento = "10/11/2021",
                    peso = 11.2,
                    fotoUrl = null,
                    alergias = mutableListOf("Pollo", "Maíz"),
                    antecedentes = mutableListOf()
                ),
                Mascota(
                    nombre = "Coco",
                    especie = "Ave",
                    raza = "Cacatúa",
                    fechaNacimiento = "23/03/2022",
                    peso = 0.3,
                    fotoUrl = null,
                    alergias = mutableListOf(),
                    antecedentes = mutableListOf("Fractura de ala derecha en 2023")
                )
            )
            mascotaDao.insertAll(mascotasDeEjemplo)
            marcarDatosDeEjemploCargados()
        }
    }

    // Agregar mascota
    fun agregarMascota(mascota: Mascota) {
        viewModelScope.launch {
            mascotaDao.insert(mascota)
        }
    }

    // Editar mascota
    fun editarMascota(mascotaEditada: Mascota) {
        viewModelScope.launch {
            mascotaDao.update(mascotaEditada)
        }
    }

    // Eliminar mascota
    fun eliminarMascota(mascota: Mascota) {
        viewModelScope.launch {
            mascotaDao.delete(mascota)
        }
    }
}