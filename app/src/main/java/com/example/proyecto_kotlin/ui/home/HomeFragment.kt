package com.example.proyecto_kotlin.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_kotlin.DetalleMascotaActivity
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
        adapter = MascotaAdapter(emptyList()) { mascota ->
            val intent = Intent(requireContext(), DetalleMascotaActivity::class.java)
            intent.putExtra("MASCOTA_ID", mascota.id)
            startActivity(intent)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewMascotas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val btnAgregarMascota = view.findViewById<Button>(R.id.btnAgregarMascota)
        btnAgregarMascota.setOnClickListener {
            val dialog = AgregarMascotaDialog { nuevaMascota ->
                homeViewModel.agregarMascota(nuevaMascota)
            }
            dialog.show(parentFragmentManager, "AgregarMascotaDialog")
        }

        homeViewModel.mascotas.observe(viewLifecycleOwner) { mascotas ->
            adapter.updateData(mascotas)
        }

        return view
    }
}