package com.dev.trackerinv.domain.usecase

import android.content.Context
import android.os.Environment
import com.dev.trackerinv.data.model.Purchase
import com.dev.trackerinv.data.model.Sale
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.File
import java.io.FileOutputStream

class GenerateReportUseCase {
    operator fun invoke(
        sales: List<Sale>,
        purchases: List<Purchase>,
        context: Context
    ): File {
        val workbook = HSSFWorkbook()
        val sheet: HSSFSheet = workbook.createSheet("Sales_Purchase_Report")

        // Create headers
        val headerRow: HSSFRow = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("Type")
        headerRow.createCell(1).setCellValue("Date")
        headerRow.createCell(2).setCellValue("Amount")

        // Add sales data
        var rowIndex = 1
        sales.forEach { sale ->
            val row: HSSFRow = sheet.createRow(rowIndex++)
            row.createCell(0).setCellValue("Sale")
            row.createCell(1).setCellValue(sale.date)
            row.createCell(2).setCellValue(sale.sp.toString())
        }

        // Add purchase data
        purchases.forEach { purchase ->
            val row: HSSFRow = sheet.createRow(rowIndex++)
            row.createCell(0).setCellValue("Purchase")
            row.createCell(1).setCellValue(purchase.inv_date)
            row.createCell(2).setCellValue(purchase.price_per_unit)
        }

        // Save file to Downloads directory
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val outputFile = File(downloadsDir, "Sales_Purchase_Report.xls")

        FileOutputStream(outputFile).use { fos ->
            workbook.write(fos)
            fos.flush()
        }
        workbook.close()
        return outputFile
    }
}

