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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.example.marcusfitnesstracker.utils.LocalVariables
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class SleepPatternViewModel : ViewModel() {

    @Composable
    fun SleepQualityChartScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A1620))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sleep quality",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            SleepQualityChart()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Last 7 days",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Last7DaysStat(icon = Icons.Default.Person, value = "47%", label = "You", modifier = Modifier.weight(1f))
                    Last7DaysStat(icon = Icons.Default.Person, value = "62%", label = "Japan, Lowest", modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Last7DaysStat(icon = Icons.Default.Person, value = "74%", label = "United States", modifier = Modifier.weight(1f))
                    Last7DaysStat(icon = Icons.Default.Person, value = "80%", label = "New Zealand, Highest", modifier = Modifier.weight(1f))
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
                    setBackgroundColor(Color(0xFF0A1620).toArgb()) // Background color
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
                        axisMaximum = 100f  // Assuming you are showing percentages (0 to 100)
                        textColor = androidx.compose.ui.graphics.Color.White.toArgb()
                    }
                    axisRight.isEnabled = false  // Disable right axis

                    // Assuming LocalVariables.sleepData has exactly 7 data points (one for each day)
                    val entries = convertToBarEntries(LocalVariables.sleepData)  // This should be a list of 7 data points

                    // Bar dataset for the sleep data
                    val dataSet = BarDataSet(entries, "Sleep Quality").apply {
                        color = androidx.compose.ui.graphics.Color.Yellow.toArgb()
                        valueTextColor = androidx.compose.ui.graphics.Color.White.toArgb()
                        valueTextSize = 12f
                    }

                    // Create BarData with the dataSet and set the bar width
                    data = BarData(dataSet).apply { barWidth = 0.2f }

                    invalidate()  // Refresh the chart to apply the data
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(androidx.compose.ui.graphics.Color.Black) // Background for the chart
        )
    }

    fun convertToBarEntries(sleepData: List<Float>, scaleFactor: Float = 10f): List<BarEntry> {
        return sleepData.mapIndexed { index, value ->
            BarEntry(index.toFloat(), value * scaleFactor)  // Scale the value to fit better in the chart
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
        SleepQualityChartScreen()
    }

}