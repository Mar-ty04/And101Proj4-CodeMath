package com.example.and101proj4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextBillAmount: EditText
    private lateinit var radioGroupTipPercentage: RadioGroup
    private lateinit var buttonCalculate: Button
    private lateinit var textViewTipAmount: TextView
    private lateinit var textViewTotalAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        editTextBillAmount = findViewById(R.id.editTextBillAmount)
        radioGroupTipPercentage = findViewById(R.id.radioGroupTipPercentage)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        textViewTipAmount = findViewById(R.id.textViewTipAmount)
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount)

        // Set up button click listener
        buttonCalculate.setOnClickListener {
            calculateTip()
        }
    }

    private fun calculateTip() {
        // Get the bill amount from EditText
        val billAmountText = editTextBillAmount.text.toString()

        // Validate input
        if (billAmountText.isEmpty()) {
            Toast.makeText(this, "Please enter a bill amount", Toast.LENGTH_SHORT).show()
            return
        }

        val billAmount = billAmountText.toDoubleOrNull()
        if (billAmount == null || billAmount <= 0) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            return
        }

        // Get selected tip percentage from RadioGroup
        val selectedRadioButtonId = radioGroupTipPercentage.checkedRadioButtonId
        if (selectedRadioButtonId == -1) {
            Toast.makeText(this, "Please select a tip percentage", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
        val tipPercentage = when (selectedRadioButton.id) {
            R.id.radioButton10 -> 0.10
            R.id.radioButton15 -> 0.15
            R.id.radioButton18 -> 0.18
            R.id.radioButton20 -> 0.20
            else -> 0.15
        }

        // Calculate tip and total
        val tipAmount = billAmount * tipPercentage
        val totalAmount = billAmount + tipAmount

        // Format currency
        val currencyFormat = NumberFormat.getCurrencyInstance()

        // Display results
        textViewTipAmount.text = currencyFormat.format(tipAmount)
        textViewTotalAmount.text = currencyFormat.format(totalAmount)

        // Show success message
        Toast.makeText(
            this,
            "Tip calculated successfully!",
            Toast.LENGTH_SHORT
        ).show()
    }
}