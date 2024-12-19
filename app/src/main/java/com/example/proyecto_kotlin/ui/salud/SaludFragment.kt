package com.example.proyecto_kotlin.ui.salud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_kotlin.R
import com.example.proyecto_kotlin.databinding.FragmentSaludBinding
import com.example.proyecto_kotlin.ui.home.HomeViewModel

class SaludFragment : Fragment() {

    private var _binding: FragmentSaludBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: SaludAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        val view = inflater.inflate(R.layout.fragment_salud, container, false)
        val args = SaludFragmentArgs.fromBundle(requireArguments())
        val mascotaId = args.mascotaId

        val mascota = homeViewModel.mascotas.value?.find { it.id == mascotaId }
        adapter = if (mascota != null) {
            SaludAdapter(mascota.consultas)
        } else {
            SaludAdapter(emptyList())
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewSalud)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}