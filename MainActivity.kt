package com.example.operators

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.operators.ui.theme.OperatorsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OperatorsTheme {
                ScaffoldView(modifier =  Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun ScaffoldView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf ("") }
    var result by remember { mutableStateOf("") }
    var selectedOperator by remember { mutableStateOf("+") }

    fun calculateResult(){
        val num1 = firstNumber.toDoubleOrNull()?:0.0
        val num2 = secondNumber.toDoubleOrNull()?:0.0

        result = when (selectedOperator) {
            "+" -> (num1 + num2).toString()
            "-" -> (num1 - num2).toString()
            "*" -> (num1 * num2).toString()
            "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Error"
            "%" -> if (num2 != 0.0) (num1 % num2).toString() else "Error"
            else -> "Invallid Operator"
        }
    }
    LazyColumn (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        item {
            Text(text = "Operator Calculator")
        }
        // First number
        item {
            TextField(
                value = firstNumber,
                onValueChange = {
                    firstNumber = it
                    calculateResult()
                },
                label = { Text(text = " Enter first number") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
        // Second number
        item {
            TextField(
                value = secondNumber,
                onValueChange = {
                    secondNumber = it
                    calculateResult()
                },
                label = { Text(text = " Enter second number") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item{
            Text(text = "Select operator")
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row (
                    modifier =  Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    listOf("+","-", "*", "/", "%").forEach {
                            operator ->
                        Button(
                            onClick = {
                                selectedOperator = operator
                                calculateResult()
                            },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = if (selectedOperator == operator) androidx.compose.ui.graphics.Color.Green  else androidx.compose.ui.graphics.Color.Gray
                            ),
                            modifier = Modifier.weight((1f))
                        ) {
                            Text(text = operator)
                        }
                    }
                }
            }
        }



        item {

            Text(text = "Result: $result")
        }
        item{
            Button(
                onClick = {
                    firstNumber = ""
                    secondNumber = ""
                    result = ""
                    selectedOperator = "+"
                    Toast.makeText(context, "Cleared", Toast.LENGTH_SHORT).show()
                },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Red
                )
            ){
                Text(text = "Clear")
            }
        }
    }
}
