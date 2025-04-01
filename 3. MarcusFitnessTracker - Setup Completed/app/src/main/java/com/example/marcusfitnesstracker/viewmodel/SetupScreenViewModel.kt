package com.example.marcusfitnesstracker.viewmodel

import UserSetupRepository
import android.app.Application
import androidx.compose.foundation.background
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
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.marcusfitnesstracker.data.AppDatabase
import com.example.marcusfitnesstracker.utils.LocalVariables
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.marcusfitnesstracker.data.UserSetup
import kotlinx.coroutines.Dispatchers


class SetupScreenViewModel : ViewModel() {

    @Composable
    fun SetupScreen(onSetupComplete: (Float, Float, Float, String, Float) -> Unit) {

        var height by remember { mutableStateOf(170f) }
        var weight by remember { mutableStateOf(70f) }
        var age by remember { mutableStateOf(25f) }
        var targetSteps by remember { mutableStateOf(8000f) }
        var gender by remember { mutableStateOf("Male") }

        val stepValues = listOf(5000f, 6000f, 7000f, 8000f, 9000f, 10000f)

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

//            // Target Steps Slider
//            Text("Target Steps: ${targetSteps.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
//            Slider(
//                value = targetSteps,
//                onValueChange = { targetSteps = it },
//                valueRange = 5000f..10000f,
//                steps = 5,
//                modifier = Modifier.fillMaxWidth()
//            )
            // Target Steps Slider
            Text("Target Steps: ${targetSteps.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.Medium)

            Slider(
                value = targetSteps,
                onValueChange = { newValue ->
                    targetSteps = stepValues.minByOrNull { abs(it - newValue) } ?: targetSteps
                },
                valueRange = 5000f..10000f,
                steps = stepValues.size - 2, // Steps should be total points minus start & end
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Continue Button
            Button(
                onClick = {
                    onSetupComplete(targetSteps, age, height, gender, weight)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Text("Continue", fontSize = 18.sp, color = Color.White)
            }
        }

    }

    @Composable
    fun StartSetup(startSetup: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome to the Setup!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Complete this setup to continue.")
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { startSetup() }) {
                Text(text = "Get Started")
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