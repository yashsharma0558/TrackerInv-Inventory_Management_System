package com.dev.trackerinv.domain.model

enum class ChartType(val displayName: String) {

    BAR_CHART("Bar Chart"),
    PIE_CHART("Pie Chart");


    override fun toString(): String {
        return displayName
    }
}