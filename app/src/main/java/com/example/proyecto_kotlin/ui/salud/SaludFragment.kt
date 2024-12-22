package com.example.proyecto_kotlin.ui.salud

import SharedMascotaViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_kotlin.Mascota
import com.example.proyecto_kotlin.databinding.FragmentSaludBinding
import com.example.proyecto_kotlin.ui.ficha.FichaFragmentDirections
import com.example.proyecto_kotlin.ui.home.HomeViewModel

class SaludFragment : Fragment() {

    private var _binding: FragmentSaludBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SaludViewModel
    private lateinit var adapter: SaludAdapter
    private var mascota: Mascota? = null
    private lateinit var sharedViewModel : SharedMascotaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaludBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SaludViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedMascotaViewModel::class.java]


        adapter = SaludAdapter(emptyList())
        binding.recyclerViewSalud.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSalud.adapter = adapter

        sharedViewModel.mascotaSeleccionada.observe(viewLifecycleOwner){ mascota ->
            if(mascota == null){
                binding.textViewTitulo.text = "Ninguna mascota seleccionada"
                binding.recyclerViewSalud.isVisible = false
                binding.textSalud.isVisible = true
                binding.buttonVerGrafico.isVisible = false
            } else {
                binding.textViewTitulo.text = "Consultas Médicas de ${mascota.nombre}"
                cargarConsultas(mascota)
            }
        }

        binding.buttonVerGrafico.setOnClickListener {
            sharedViewModel.mascotaSeleccionada.value?.let { mascota ->
                val action = SaludFragmentDirections.actionNavSaludToNavGrafico(mascota.id)
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

    private fun cargarConsultas(mascota: Mascota) {
        viewModel.consultas.observe(viewLifecycleOwner) { consultas ->
            val consultasFiltradas = consultas.filter { it.mascotaId == mascota.id }
            if (consultasFiltradas.isEmpty()){
                binding.textSalud.isVisible = false
            } else {
                binding.textSalud.isVisible = false
                binding.recyclerViewSalud.isVisible = true
                adapter.refreshData(consultasFiltradas)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}