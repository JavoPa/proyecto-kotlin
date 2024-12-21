package com.example.proyecto_kotlin.ui.salud.grafico

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto_kotlin.databinding.FragmentGraficoBinding
import com.example.proyecto_kotlin.ui.salud.Consulta
import com.example.proyecto_kotlin.ui.salud.SaludFragmentArgs
import com.example.proyecto_kotlin.ui.salud.SaludViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.util.Calendar

class GraficoFragment : Fragment() {

    private var _binding: FragmentGraficoBinding? = null
    private val binding get() = _binding!!

    private lateinit var consultas: List<Consulta>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGraficoBinding.inflate(inflater, container, false)
        val saludViewModel = ViewModelProvider(requireActivity())[SaludViewModel::class.java]

        val args = SaludFragmentArgs.fromBundle(requireArguments())
        val mascotaId = args.mascotaId

        consultas = saludViewModel.consultas.value?.filter { it.mascotaId == mascotaId } ?: emptyList()

        val consultasPorAnio = consultas
            .groupBy {
                val calendar = Calendar.getInstance()
                calendar.time = it.fecha
                calendar.get(Calendar.YEAR)
            }
            .map { entry -> entry.key to entry.value.size }

        val entries = consultasPorAnio.mapIndexed { index, pair ->
            BarEntry(index.toFloat(), pair.second.toFloat())
        }

        val dataSet = BarDataSet(entries, "Consultas por Año")
        val barData = BarData(dataSet)

        val barChart: BarChart = binding.grafico
        barChart.data = barData
        barChart.invalidate()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}