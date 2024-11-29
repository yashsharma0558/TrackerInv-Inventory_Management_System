package com.dev.trackerinv.ui.screen.report.invhealth

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dev.trackerinv.InventoryApp
import com.dev.trackerinv.databinding.FragmentInventoryHealthBinding
import com.dev.trackerinv.domain.model.ChartType
import com.dev.trackerinv.ui.utils.DatePickerUtil.showDatePicker
import com.dev.trackerinv.ui.viewmodel.ReportViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter

class InventoryHealthFragment : Fragment() {
    private lateinit var viewModel: ReportViewModel
    private lateinit var binding: FragmentInventoryHealthBinding
    private var startDate: String = ""
    private var endDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as InventoryApp).reportViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentInventoryHealthBinding.inflate(inflater, container, false)
        setupChartDropdown()
        val barChart = binding.barChart
        val pieChart = binding.pieChart
        // Set up date pickers
        binding.startDatePicker.setOnClickListener {
            showDatePicker { date ->
                startDate = date
                binding.startDatePicker.text = date
            }
        }

        binding.endDatePicker.setOnClickListener {
            showDatePicker { date ->
                endDate = date
                binding.endDatePicker.text = date
            }
        }
        binding.generateChart.setOnClickListener {
            val startDate = binding.startDatePicker.text.toString()
            val endDate = binding.endDatePicker.text.toString()
            val selectedChart = binding.spinnerChartType.selectedItem.toString()
            if(selectedChart == ChartType.BAR_CHART.toString()){
                barChart.visibility = View.VISIBLE
                pieChart.visibility = View.GONE
                viewModel.fetchBarChartData(
                    startDate = startDate,
                    endDate = endDate,
                    onSuccess = { barChartData ->
                        // Step 1: Map sales and purchases data to BarEntries
                        val barEntries = barChartData.salesQuantities.mapIndexed { index, salesQuantity ->
                            val purchaseQuantity = barChartData.purchaseQuantities.getOrNull(index) ?: 0
                            BarEntry(index.toFloat(), floatArrayOf(salesQuantity.toFloat(), purchaseQuantity.toFloat()))
                        }

                        // Step 2: Prepare data set with red and blue colors
                        val barDataSet = BarDataSet(barEntries, "Inventory Status").apply {
                            stackLabels = arrayOf("Sales", "Purchases")
                            colors = listOf(
                                Color.RED,  // Red for Sales
                                Color.CYAN  // Blue for Purchases
                            )
                        }

                        // Step 3: Create BarData
                        val barData = BarData(barDataSet)
                        barData.barWidth = 0.4f // Adjust the width of bars if needed

                        val xAxis = barChart.xAxis
                        xAxis.position = XAxis.XAxisPosition.BOTTOM // Position x-axis at the bottom
                        xAxis.valueFormatter = IndexAxisValueFormatter(barChartData.labels) // Use dates as labels
                        xAxis.granularity = 1f // Ensure labels are evenly spaced

                        // Step 5: Configure y-axis
                        barChart.axisLeft.axisMinimum = 0f // Start y-axis from 0
                        barChart.axisRight.isEnabled = false // Disable the right y-axis

                        // Step 6: Set up chart and display data
                        barChart.data = barData
                        barChart.description.isEnabled = false // Disable chart description
                        barChart.setFitBars(true) // Ensure bars fit properly
                        barChart.invalidate() // Refresh the chart

                        barChart.animateY(1000, Easing.EaseInOutQuad) // Animate the y-axis values over 1 second
                        barChart.animateX(1000, Easing.EaseInOutQuad) // Optionally animate the x-axis (horizontal) as well
                    },
                    onError = { exception ->
                        // Handle error (e.g., show a toast or dialog)
                        Log.e("BarChartError", "Error: ${exception.message}")
                    }
                )
            } else {
                pieChart.visibility = View.VISIBLE
                barChart.visibility = View.GONE
                viewModel.fetchPieChartData(
                    startDate = startDate,
                    endDate = endDate,
                    onSuccess = { pieChartData ->
                        // Prepare entries for the PieChart
                        val pieEntries = listOf(
                            PieEntry(pieChartData.totalSales.toFloat(), "Sales"),
                            PieEntry(pieChartData.totalPurchases.toFloat(), "Purchases")
                        )

                        // Create PieDataSet
                        val pieDataSet = PieDataSet(pieEntries, "Inventory Status").apply {
                            colors = listOf(Color.RED, Color.CYAN) // Red for Sales, Cyan for Purchases
                            sliceSpace = 2f                       // Space between slices
                            valueTextColor = Color.WHITE          // Text color on slices
                            valueTextSize = 14f                   // Text size
                        }

                        // Create PieData
                        val pieData = PieData(pieDataSet).apply {
                            setValueFormatter(PercentFormatter()) // Display values as percentages
                        }

                        // Configure PieChart
                        pieChart.data = pieData
                        pieChart.description.isEnabled = false
                        pieChart.isDrawHoleEnabled = true // Draw a hole in the center
                        pieChart.setUsePercentValues(true) // Display percentages
                        pieChart.centerText = "Inventory Health"
                        pieChart.animateY(1000, Easing.EaseInOutQuad) // Animation

                        pieChart.invalidate() // Refresh the PieChart
                    },
                    onError = { exception ->
                        Log.e("PieChartError", "Error: ${exception.message}")
                    }
                )

            }

        }
        return binding.root
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        showDatePicker(requireContext(), onDateSelected)
    }


    private fun setupChartDropdown() {
        val chartNames = viewModel.chartType.map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, chartNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerChartType.adapter = adapter
    }

}