package com.dev.trackerinv.ui.screen.report.financial

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.dev.trackerinv.InventoryApp
import com.dev.trackerinv.R
import com.dev.trackerinv.databinding.FragmentFinancialReportBinding
import com.dev.trackerinv.domain.model.RevenueTrend
import com.dev.trackerinv.ui.viewmodel.ReportViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class FinancialReportFragment : Fragment() {
    private lateinit var viewModel: ReportViewModel
    private lateinit var binding: FragmentFinancialReportBinding
    private lateinit var chart: LineChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as InventoryApp).reportViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinancialReportBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        viewModel.fetchFinancialSummary()
        viewModel.fetchRevenueTrends()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val totalProfit = binding.tvProfit
        val totalPurchases = binding.tvTotalPurchases
        val totalSales = binding.tvTotalSales
        chart = binding.revenueChart
        viewModel.financialSummary.observe(viewLifecycleOwner) { financialSummary ->
            totalProfit.text = financialSummary.profit.toString()
            totalPurchases.text = financialSummary.totalPurchases.toString()
            totalSales.text = financialSummary.totalSales.toString()
        }
        viewModel.revenueTrends.observe(viewLifecycleOwner) {
            trends ->
            updateLineChart(trends)
        }
    }

    private fun updateLineChart(trends: List<RevenueTrend>) {
        val saleEntries = trends.mapIndexed { index, trend ->
            Entry(index.toFloat(), trend.totalSales.toFloat())
        }
        val purchaseEntries = trends.mapIndexed { index, trend ->
            Entry(index.toFloat(), trend.totalPurchases.toFloat())
        }

        val salesDataSet = LineDataSet(saleEntries, "Sale Revenue").apply {
            color = Color.CYAN
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
            lineWidth = 2f
            circleRadius = 4f
            setDrawFilled(true)
            fillColor = Color.CYAN
        }
        val purchasesDataSet = LineDataSet(purchaseEntries, "Purchase Revenue").apply {
            color = Color.RED
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.black)
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(Color.RED)
            setDrawFilled(true)
            fillColor = Color.RED
        }

        val lineData = LineData(salesDataSet, purchasesDataSet)
        chart.apply {
            data = lineData
            animateX(500)
            animateY(1500)
            description.text = "Revenue Trends"
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.labelRotationAngle = (-30f)
            xAxis.valueFormatter = IndexAxisValueFormatter(trends.map { it.month })
            axisRight.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            invalidate()
        }
    }
}