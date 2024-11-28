package com.dev.trackerinv.ui.screen.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.trackerinv.R
import com.dev.trackerinv.databinding.FragmentReportBinding
import com.dev.trackerinv.domain.model.ReportItem
import com.dev.trackerinv.ui.adapter.ReportAdapter

class ReportFragment : Fragment() {

    private lateinit var binding: FragmentReportBinding
    private lateinit var reportAdapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reportItems = ReportItem.values().toList() // Enum values as list

        reportAdapter = ReportAdapter(reportItems) { reportItem ->
            handleReportItemClick(reportItem)
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns for grid layout
            adapter = reportAdapter
        }
    }

    private fun handleReportItemClick(reportItem: ReportItem) {
        when (reportItem) {
            ReportItem.GENERATE_REPORT -> {
//                Toast.makeText(requireContext(), "Generate Report Clicked", Toast.LENGTH_SHORT).show()
                // Navigate or perform logic
                findNavController().navigate(R.id.action_reportFragment_to_generateReportFragment)
            }
            ReportItem.INVENTORY_HEALTH -> {
//                Toast.makeText(requireContext(), "Inventory Health Clicked", Toast.LENGTH_SHORT).show()
                // Navigate or perform logic
                findNavController().navigate(R.id.action_reportFragment_to_inventoryHealthFragment)
            }
            ReportItem.FINANCIAL_REPORT -> {
//                Toast.makeText(requireContext(), "Financial Report Clicked", Toast.LENGTH_SHORT).show()
                // Navigate or perform logic
                findNavController().navigate(R.id.action_reportFragment_to_financialReportFragment)
            }
        }
    }
}
