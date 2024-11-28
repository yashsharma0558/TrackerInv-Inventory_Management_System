package com.dev.trackerinv.domain.model

enum class ReportItem(val title: String, val description: String) {
    GENERATE_REPORT("Generate Report", "Generate a report of sales and purchases"),
    INVENTORY_HEALTH("Inventory Health", "Check the health of the inventory"),
    FINANCIAL_REPORT("Financial Report", "Generate a financial report")
}