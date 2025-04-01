import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path.Direction.*
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
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
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler
import kotlin.io.path.Path

class SleepPatterns : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SleepQualityChartScreen()
        }
    }
}

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
                setBackgroundColor(Color(0xFF0A1620).toArgb())
                description.isEnabled = false

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    granularity = 1f
                    textColor = androidx.compose.ui.graphics.Color.White.toArgb()
                    valueFormatter = IndexAxisValueFormatter(
                        listOf("Sun\n6/7", "Mon\n6/8", "Wed\n6/10", "Tue\n6/16", "Wed\n6/17", "Mon\n6/22", "Tue\n6/23")
                    )
                }

                axisLeft.apply {
                    axisMinimum = 0f
                    axisMaximum = 100f
                    textColor = androidx.compose.ui.graphics.Color.White.toArgb()
                }
                axisRight.isEnabled = false

                val entries = listOf(
                    BarEntry(0f, 40f),
                    BarEntry(1f, 60f),
                    BarEntry(2f, 50f),
                    BarEntry(3f, 35f),
                    BarEntry(4f, 10f),
                    BarEntry(5f, 45f),
                    BarEntry(6f, 50f)
                )
                val dataSet = BarDataSet(entries, "Sleep Quality").apply {
                    color = androidx.compose.ui.graphics.Color.Yellow.toArgb()
                    valueTextColor = androidx.compose.ui.graphics.Color.White.toArgb()
                    valueTextSize = 12f
                }
                data = BarData(dataSet).apply { barWidth = 0.2f }

                invalidate()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(androidx.compose.ui.graphics.Color.Black)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSleepPatterns() {
    SleepQualityChartScreen()
}