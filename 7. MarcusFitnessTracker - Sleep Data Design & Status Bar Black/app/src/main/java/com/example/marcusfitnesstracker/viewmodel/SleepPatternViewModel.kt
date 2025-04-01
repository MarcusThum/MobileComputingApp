package com.example.marcusfitnesstracker.viewmodel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.marcusfitnesstracker.utils.LocalVariables
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class SleepPatternViewModel : ViewModel() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RecipeAppBar(navController: NavController, title: String) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // ensure text is visible on black
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White // icon color
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* Handle menu or settings */ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color.White // icon color
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black
            )
        )
    }


    @Composable
    fun SleepQualityChartScreen(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {

            RecipeAppBar(navController, "")

            // Top Title
            Text(
                text = "Sleep",
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Avg Time In Bed & Asleep Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatBlock("AVG. TIME AWAKE", "${(8 - LocalVariables.lastSleepTime).toInt() } hr ${(((8 - LocalVariables.lastSleepTime) - (8 - LocalVariables.lastSleepTime).toInt())*60).toInt() } mins")
                StatBlock("AVG. TIME ASLEEP", "${LocalVariables.lastSleepTime.toInt()} hr ${((LocalVariables.lastSleepTime - LocalVariables.lastSleepTime.toInt())*60).toInt()} mins")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Bar Chart
            SleepQualityChart()

            Spacer(modifier = Modifier.height(16.dp))

            // Sleep Goal
            RoundedInfoBar("Sleep Goal", "8 hr")

            Spacer(modifier = Modifier.height(8.dp))

            // Sleep Schedule
            RoundedInfoBar("Sleep Schedule", "Multiple")

            Spacer(modifier = Modifier.height(16.dp))

            // Additional Schedule – e.g. Weekends
            SleepScheduleCard(bedTime = "11:30 PM", wakeUpTime = "8:00 AM")

            Spacer(modifier = Modifier.height(12.dp))

            // Another one (custom label version below)
            LabeledSleepScheduleCard(
                label = "Workdays",
                bedTime = "10:00 PM",
                wakeUpTime = "6:00 AM"
            )

            Spacer(modifier = Modifier.height(12.dp))

            LabeledSleepScheduleCard(
                label = "Weekends",
                bedTime = "12:00 AM",
                wakeUpTime = "9:00 AM"
            )

        }
    }

    @Composable
    fun StatBlock(title: String, value: String) {
        Column {
            Text(text = title, fontSize = 12.sp, color = Color.LightGray)
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }

    @Composable
    fun RoundedInfoBar(label: String, value: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1C1C1E), shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 16.sp, color = Color.White)
            Text(text = value, fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }

    @Composable
    fun SleepScheduleCard(bedTime: String, wakeUpTime: String) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2C2C2E), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(text = "Your Schedule", color = Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("BEDTIME", fontSize = 12.sp, color = Color.Gray)
                    Text(bedTime, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text("WAKE UP", fontSize = 12.sp, color = Color.Gray)
                    Text(wakeUpTime, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }


    @Composable
    fun LabeledSleepScheduleCard(label: String, bedTime: String, wakeUpTime: String) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2C2C2E), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(text = label, color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("BEDTIME", fontSize = 12.sp, color = Color.Gray)
                    Text(bedTime, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text("WAKE UP", fontSize = 12.sp, color = Color.Gray)
                    Text(wakeUpTime, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }


    @Composable
    fun Last7DaysStat(icon: ImageVector, value: String, label: String, modifier: Modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = value, fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = label, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }


    @Composable
    fun SleepQualityChart() {
        AndroidView(
            factory = { context ->
                BarChart(context).apply {
                    setBackgroundColor(Color.Black.toArgb()) // Background color
                    description.isEnabled = false // Disable chart description



                    // X Axis Settings
                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        setDrawGridLines(false)
                        granularity = 1f
                        textColor = androidx.compose.ui.graphics.Color.White.toArgb()

                        // Label the X-Axis with the days of the week: Mon-Sun
                        valueFormatter = IndexAxisValueFormatter(
                            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                        )
                    }

                    // Y Axis Settings
                    axisLeft.apply {
                        axisMinimum = 0f
                        axisMaximum = 9f  // Assuming you are showing percentages (0 to 100)
                        textColor = androidx.compose.ui.graphics.Color.White.toArgb()
                        valueFormatter = object : ValueFormatter() {
                            override fun getFormattedValue(value: Float): String {
                                return "${value.toInt()} hr"
                            }
                        }
                    }
                    axisRight.isEnabled = false  // Disable right axis

                    // Assuming LocalVariables.sleepData has exactly 7 data points (one for each day)
                    val entries = convertToBarEntries(LocalVariables.sleepData)  // This should be a list of 7 data points

                    // Bar dataset for the sleep data
                    val dataSet = BarDataSet(entries, "Sleep Quality").apply {
                        color = android.graphics.Color.rgb(0, 230, 245) // A bright cyan/teal shade
//                        valueTextColor = androidx.compose.ui.graphics.Color.White.toArgb()
//                        valueTextSize = 12f
                        setDrawValues(false) // ← This disables text above bars
                    }

                    // Create BarData with the dataSet and set the bar width
                    data = BarData(dataSet).apply { barWidth = 0.6f }

                    invalidate()  // Refresh the chart to apply the data
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(androidx.compose.ui.graphics.Color.Black) // Background for the chart
        )
    }

    fun convertToBarEntries(sleepData: List<Float>, scaleFactor: Float = 0f): List<BarEntry> {
        return sleepData.mapIndexed { index, value ->
            BarEntry(index.toFloat(), value )  // Scale the value to fit better in the chart
        }
    }




//    @Composable
//    fun SleepQualityChart() {
//        AndroidView(
//            factory = { context ->
//                BarChart(context).apply {
//                    setBackgroundColor(Color(0xFF0A1620).toArgb())
//                    description.isEnabled = false
//
//                    xAxis.apply {
//                        position = XAxis.XAxisPosition.BOTTOM
//                        setDrawGridLines(false)
//                        granularity = 1f
//                        textColor = androidx.compose.ui.graphics.Color.White.toArgb()
//                        valueFormatter = IndexAxisValueFormatter(
//                            listOf("Sun\n6/7", "Mon\n6/8", "Wed\n6/10", "Tue\n6/16", "Wed\n6/17", "Mon\n6/22", "Tue\n6/23")
//                        )
//                    }
//
//                    axisLeft.apply {
//                        axisMinimum = 0f
//                        axisMaximum = 100f
//                        textColor = androidx.compose.ui.graphics.Color.White.toArgb()
//                    }
//                    axisRight.isEnabled = false
//
//                    val entries = convertToBarEntries(LocalVariables.sleepData)
//                    val dataSet = BarDataSet(entries, "Sleep Quality").apply {
//                        color = androidx.compose.ui.graphics.Color.Yellow.toArgb()
//                        valueTextColor = androidx.compose.ui.graphics.Color.White.toArgb()
//                        valueTextSize = 12f
//                    }
//                    data = BarData(dataSet).apply { barWidth = 0.2f }
//
//                    invalidate()
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .background(androidx.compose.ui.graphics.Color.Black)
//        )
//    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewSleepPatterns() {
//        SleepQualityChartScreen()
    }

}