package com.dev.trackerinv.ui.utils

import android.content.Context
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*

object ChartUtil {

    // Configure and populate a bar chart
    fun setupBarChart(
        barChart: BarChart,
        barData: BarData,
        context: Context
    ) {
        barChart.apply {
            data = barData
            description.isEnabled = false
            setFitBars(true)
            animateY(1000)

            // Custom styling
            axisLeft.setDrawGridLines(false)
            axisRight.isEnabled = false
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f
            legend.isEnabled = true
        }
        barChart.invalidate() // Refresh the chart
    }

    // Configure and populate a pie chart
    fun setupPieChart(
        pieChart: PieChart,
        pieData: PieData,
        context: Context
    ) {
        pieChart.apply {
            data = pieData
            description.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 40f
            setTransparentCircleAlpha(110)
            animateY(1000)

            // Custom styling
            legend.isEnabled = true
        }
        pieChart.invalidate() // Refresh the chart
    }
}
