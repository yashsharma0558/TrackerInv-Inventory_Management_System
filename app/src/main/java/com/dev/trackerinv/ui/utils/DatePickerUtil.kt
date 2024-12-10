package com.dev.trackerinv.ui.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.log

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
                var date = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                if(selectedDay< 10){
                    date = "$selectedYear-${selectedMonth + 1}-0$selectedDay"
                }
                if (selectedMonth < 10){
                    date = "$selectedYear-0${selectedMonth + 1}-$selectedDay"
                }


                onDateSelected(date)

            },
            year,
            month,
            day
        )

        // Showing the date picker dialog
        datePickerDialog.show()
    }
}