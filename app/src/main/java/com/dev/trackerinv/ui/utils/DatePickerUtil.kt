package com.dev.trackerinv.ui.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.TextView
import java.util.Calendar

object DatePickerUtil {

    // Function to show a date picker dialog and set selected date to TextView
    fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
        // Getting the current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Creating a DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Setting the selected date to the TextView
                val selectedDate = "$selectedDay-${selectedMonth + 1}-$selectedYear"
                onDateSelected(selectedDate)
            },
            year,
            month,
            day
        )

        // Showing the date picker dialog
        datePickerDialog.show()
    }
}