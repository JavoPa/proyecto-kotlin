package com.example.proyecto_kotlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_kotlin.R

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: MascotaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Configurar el adaptador del RecyclerView
        adapter = MascotaAdapter(emptyList()) { mascota ->
            // Utilizar NavController para navegar a FichaFragment con el argumento mascotaId
            val action = HomeFragmentDirections.actionNavHomeToNavFicha(mascota.id)
            findNavController().navigate(action)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewMascotas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Botón para agregar nueva mascota
        val btnAgregarMascota = view.findViewById<Button>(R.id.btnAgregarMascota)
        btnAgregarMascota.setOnClickListener {
            val dialog = AgregarMascotaDialog { nuevaMascota ->
                homeViewModel.agregarMascota(nuevaMascota)
            }
            dialog.show(parentFragmentManager, "AgregarMascotaDialog")
        }

        // Observar los cambios en la lista de mascotas
        homeViewModel.mascotas.observe(viewLifecycleOwner) { mascotas ->
            adapter.updateData(mascotas)
        }

        return view
    }
}