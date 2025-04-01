package com.example.marcusfitnesstracker.viewmodel

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.marcusfitnesstracker.R
import com.example.marcusfitnesstracker.utils.LocalVariables
import kotlin.random.Random

class FitnessViewModel : ViewModel() {
    @Composable
    fun HealthTrackerUI() {

        var stepCount by remember { mutableStateOf(LocalVariables.todayTotalSteps) } // Example: 4000 steps taken
        var targetSteps = remember { LocalVariables.targetSteps } // Step goal
        var progress = remember { stepCount.toFloat() } / targetSteps.toFloat()
        var distance = remember { LocalVariables.distance }
        var caloriesBurned = remember { LocalVariables.caloriesBurned }
        var averageHeartRate = remember { LocalVariables.averageHeartRate }
        var lastSleepTime = remember { LocalVariables.lastSleepTime }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Adds space between boxes
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
            ) {
                // 🔥 Set Image as Background
                Image(
                    painter = painterResource(id = R.drawable.home_background_image), // Replace with your image name
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop, // Makes the image cover the screen
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressBar(progress, stepCount, targetSteps)

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        StatCard("Target Step", targetSteps.toString())
                        StatCard("Distance (km)", distance.toString())
                        StatCard("Heat (kcal)", caloriesBurned.toString())
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Spacer(modifier = Modifier.height(20.dp))

                    SectionHeader("Average Heart Rate")
                    Text(text = "$averageHeartRate bpm", fontSize = 18.sp, color = Color.White)

                    Spacer(modifier = Modifier.height(20.dp))

                    SectionHeader("Last Sleep Record")
                    Text(text = "$lastSleepTime h", fontSize = 18.sp, color = Color.White)
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp), // Further reduced vertical padding
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f) // Optional: Adjust width if needed
                        .height(135.dp), // Decrease height further
                    contentAlignment = Alignment.Center
                ) {
                    HeartRateRecording() // Compact heart rate section
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f) // Optional: Adjust width if needed
                        .height(135.dp), // Decrease height further
                    contentAlignment = Alignment.Center
                ) {
                    SleepRecording() // Compact heart rate section
                }
            }

        }
    }

    @Composable
    fun CircularProgressBar(progress: Float, stepCount: Int, targetSteps: Int) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)

        ) {
            Canvas(modifier = Modifier.size(180.dp)) {
                drawArc(
                    color = Color.Gray, // Background progress color
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                )

                drawArc(
                    color = Color.White, // Progress color
                    startAngle = -90f,
                    sweepAngle = progress * 360f,
                    useCenter = false,
                    style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$stepCount",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Today's Steps",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }

    @Composable
    fun HeartRateRecording() {
        var heartRateData = remember { LocalVariables.heartRateData }
        //val currentHeartRate = heartRateData.lastOrNull() ?: 0
        var currentHeartRate = remember { LocalVariables.averageHeartRate }

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home_heart_icon), // Replace with a heart icon
                        contentDescription = "Heart Icon",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Heart Rate Recording",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "$currentHeartRate bpm",
                        fontSize = 16.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                HeartRateGraph(heartRateData)
            }
        }
    }

//    @Composable
//    fun SleepRecording() {
//        val sleepData = remember { generateSleepData() }
//
//        Card(
//            shape = RoundedCornerShape(8.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(
//                    text = "Sleep Record",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                SleepGraph(sleepData)
//            }
//        }
//    }
    @Composable
    fun SleepRecording() {
        var sleepData = remember { LocalVariables.sleepData }
        //val sleepHours = (sleepData.lastOrNull() ?: 0).toInt()
        var sleepHours = remember { LocalVariables.lastSleepTime }

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Sleep Record",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "$sleepHours hrs",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                SleepGraph(sleepData)
            }
        }
    }

    @Composable
    fun SleepGraph(data: List<Float>) {
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
        ) {
            val maxSleepHours = 10f
            val widthStep = size.width / data.size

            for (i in 1 until data.size) {
                drawLine(
                    color = Color.Blue,
                    start = Offset((i - 1) * widthStep, size.height - (data[i - 1] / maxSleepHours) * size.height),
                    end = Offset(i * widthStep, size.height - (data[i] / maxSleepHours) * size.height),
                    strokeWidth = 4f
                )
            }
        }
    }


    @Composable
    fun HeartRateGraph(data: List<Int>) {
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
        ) {
            val maxHeartRate = 200
            val widthStep = size.width / data.size

            for (i in 1 until data.size) {
                drawLine(
                    color = Color.Red,
                    start = Offset((i - 1) * widthStep, size.height - (data[i - 1] / maxHeartRate.toFloat()) * size.height),
                    end = Offset(i * widthStep, size.height - (data[i] / maxHeartRate.toFloat()) * size.height),
                    strokeWidth = 4f
                )
            }
        }
    }

    @Composable
    fun StatCard(label: String, value: String) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, fontSize = 14.sp, color = Color.White)
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }

    @Composable
    fun SectionHeader(title: String) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewHealthTrackerUI() {
        HealthTrackerUI()
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewStatCard() {
        StatCard("Target Step", "8000")
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewCircularProgressBar() {
        CircularProgressBar(progress = 0.5f, stepCount = 4000, targetSteps = 8000)
    }

    @Preview
    @Composable
    fun TwoColumnsExample() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(8.dp)
            ) {
                Text(text = "First Column", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "This is the top column")
            }

            Spacer(modifier = Modifier.height(16.dp)) // Adds space between columns

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Cyan)
                    .padding(8.dp)
            ) {
                Text(text = "Second Column", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "This is the column below")
            }
        }
    }

}