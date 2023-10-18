package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    // Creating a null textView
    // Make it private (not accessible outside of class)
    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Type button, find the id
        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        // Set a click listener for the button
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    // call this method when button is, has to be outside of onCreate
    private fun clickDatePicker() {
        // gives us access to calendar
        val myCalendar = Calendar.getInstance()

        // get values from the calendar, gets current date to be displayed when calendar is opened
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            // the view, year, month, and dayOfMonth are passed into the dialog, set to variables
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                // pass in the context, text, and length

                val selectedDate = "${month + 1}/$dayOfMonth/$year"

                // question mark is safe call operator (if null)
                tvSelectedDate?.setText(selectedDate)

                // Create a simple date format object to parse
                val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)

                // Create a date object
                val theDate = sdf.parse(selectedDate)

                val selectedDateInMinutes = theDate.time / 60000

                // if date is not empty, we execute it
                theDate?.let {
                    // gives us how much time has passed since 1970, from current date
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        tvAgeInMinutes?.setText(differenceInMinutes.toString())
                    }
                }


            },
            year,
            month,
            day
            )

        // Cannot select dates in the future (does not allow the current day)
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }
}