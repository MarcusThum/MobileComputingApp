package com.example.marcusfitnesstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MealPlanning : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealPlanScreen()
        }
    }
}

@Composable
fun MealPlanScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        DateSelectionBar()
        Spacer(modifier = Modifier.height(16.dp))
        MealCategory("BREAKFAST", listOf(MealItem("Berry Blast Acai Bowl", "8 servings")))
        MealCategory("LUNCH", listOf(
            MealItem("Grilled Chicken And Vegetables", "5 servings"),
            MealItem("Sweet Potato Cake Lemony Slaw", "5 servings")
        ))
        MealCategory("DINNER", listOf(MealItem("Honey Butter Chicken Tenders", "8 servings")))
    }
}

@Composable
fun DateSelectionBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Meal Plan", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        listOf("SUN 24", "MON 25", "TUE 26", "WED 27", "THU 28", "FRI 29").forEach {
            Text(text = it, fontSize = 14.sp, color = if (it.contains("24")) Color.Green else Color.Gray)
        }
    }
}

@Composable
fun MealCategory(title: String, meals: List<MealItem>) {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(meals.size) { index ->
                MealCard(meals[index])
            }
        }
    }
}

@Composable
fun MealCard(meal: MealItem) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery), // Placeholder image
                contentDescription = null,
                modifier = Modifier.size(50.dp).padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = meal.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = meal.servings, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

data class MealItem(val name: String, val servings: String)

@Preview(showBackground = true)
@Composable
fun PreviewFoodPlanner() {
    MealPlanScreen()
}
