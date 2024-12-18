package com.example.proyecto_kotlin.ui.vacunas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto_kotlin.databinding.FragmentVacunasBinding

class VacunasFragment : Fragment() {

    private var _binding: FragmentVacunasBinding? = null
    private val binding get() = _binding!!
    private lateinit var vacunasViewModel: VacunasViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacunasBinding.inflate(inflater, container, false)
        vacunasViewModel = ViewModelProvider(this).get(VacunasViewModel::class.java)

        val root: View = binding.root

        // Recuperar el mascotaId desde SharedPreferences
        val mascotaId = obtenerMascotaSeleccionada()

        val textView: TextView = binding.textVacunas
        if (mascotaId == -1) {
            // Mostrar mensaje si no hay mascota seleccionada
            textView.text = "Seleccione una mascota para ver su historial de vacunas."
        } else {
            // Mostrar mensaje indicando la mascota seleccionada
            textView.text = "Mostrando historial de vacunas para la mascota con ID: $mascotaId"
            // Aquí puedes cargar los datos específicos de esta mascota
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obtenerMascotaSeleccionada(): Int {
        val sharedPreferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("MASCOTA_ID", -1)
    }
}
