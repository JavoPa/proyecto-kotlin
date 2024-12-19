package com.example.proyecto_kotlin.ui.vacunas

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_kotlin.databinding.FragmentVacunasBinding

class VacunasFragment : Fragment() {

    private var _binding: FragmentVacunasBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: VacunasViewModel
    private lateinit var adapter: VacunasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacunasBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(VacunasViewModel::class.java)

        val root: View = binding.root

        // Recuperar el mascotaId desde SharedPreferences
        val mascotaId = obtenerMascotaSeleccionada()

        val textView = binding.textVacunas
        if(mascotaId != null){
            textView.text = "No hay vacunas registradas para esta Mascota"
        }

        adapter = VacunasAdapter(emptyList())
        binding.recyclerViewVacunas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewVacunas.adapter = adapter

        viewModel.vacunas.observe(viewLifecycleOwner) { vacunas ->
            if (vacunas.isEmpty()) {
                binding.recyclerViewVacunas.visibility = View.GONE
                binding.textVacunas.visibility = View.VISIBLE
            } else {
                binding.recyclerViewVacunas.visibility = View.VISIBLE
                binding.textVacunas.visibility = View.GONE
                adapter.actualizarDatos(vacunas)
            }
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
