package com.example.marcusfitnesstracker.viewmodel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class SetupScreenViewModel : ViewModel() {

    @Composable
    fun SetupScreen() {
        var height by remember { mutableStateOf(170f) }
        var weight by remember { mutableStateOf(70f) }
        var age by remember { mutableStateOf(25f) }
        var targetSteps by remember { mutableStateOf(8000f) }
        var gender by remember { mutableStateOf("Male") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Setup Your Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            // Gender Selection
            Text("Select Gender", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GenderOption(
                    gender = "Male",
                    selectedGender = gender,
                    onSelect = { gender = "Male" }
                )
                GenderOption(
                    gender = "Female",
                    selectedGender = gender,
                    onSelect = { gender = "Female" }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Height Slider
            Text("Height: ${height.toInt()} cm", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Slider(
                value = height,
                onValueChange = { height = it },
                valueRange = 140f..200f,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Weight Slider
            Text("Weight: ${weight.toInt()} kg", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Slider(
                value = weight,
                onValueChange = { weight = it },
                valueRange = 40f..150f,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Age Slider
            Text("Age: ${age.toInt()} years", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Slider(
                value = age,
                onValueChange = { age = it },
                valueRange = 10f..100f,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Target Steps Slider
            Text("Target Steps: ${targetSteps.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Slider(
                value = targetSteps,
                onValueChange = { targetSteps = it },
                valueRange = 5000f..15000f,
                steps = 5,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Continue Button
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text("Continue", fontSize = 18.sp, color = Color.White)
            }
        }
    }

    @Composable
    fun GenderOption(gender: String, selectedGender: String, onSelect: () -> Unit) {
//    val image = if (gender == "Male") R.drawable.ic_male else R.drawable.ic_female
        val color = if (gender == selectedGender) Color.Blue else Color.Gray

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .clickable { onSelect() }
        ) {
            Surface(
                shape = CircleShape,
                color = color.copy(alpha = 0.3f),
                modifier = Modifier.size(80.dp)
            ) {
//            Image(
////                painter = painterResource(id = image),
//                contentDescription = gender,
//                modifier = Modifier.padding(16.dp)
//            )
            }
            Text(text = gender, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }

}