package com.example.proyecto_kotlin.ui.salud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_kotlin.Mascota
import com.example.proyecto_kotlin.databinding.FragmentSaludBinding
import com.example.proyecto_kotlin.ui.home.HomeViewModel

class SaludFragment : Fragment() {

    private var _binding: FragmentSaludBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SaludViewModel
    private lateinit var adapter: SaludAdapter
    private var mascota: Mascota? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaludBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SaludViewModel::class.java]
        val homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        val args = SaludFragmentArgs.fromBundle(requireArguments())
        val mascotaId = args.mascotaId
        mascota = homeViewModel.mascotas.value?.find { it.id == mascotaId }

        mascota?.let {
            binding.textViewTitulo.text = "Consultas Médicas de ${it.nombre}"
        } ?: run {
            binding.textSalud.text = "Ninguna mascota seleccionada"
        }

        adapter = SaludAdapter(emptyList())
        binding.recyclerViewSalud.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSalud.adapter = adapter

        viewModel.consultas.observe(viewLifecycleOwner) { consultas ->
            val consultasFiltradas = consultas.filter { it.mascotaId == mascotaId }

            if (consultasFiltradas.isEmpty()) {
                binding.textSalud.isVisible = true
            } else {
                binding.textSalud.isVisible = false
                adapter.refreshData(consultasFiltradas)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}