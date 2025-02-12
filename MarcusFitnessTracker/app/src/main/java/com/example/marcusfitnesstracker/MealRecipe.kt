package com.example.marcusfitnesstracker
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview

class MealRecipe : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeScreen()
        }
    }
}

@Composable
fun RecipeScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Food Recipes For You", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008065)),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Filter")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008065)),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Sorting")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        RecipeGrid(
            listOf(
                RecipeItem("Grilled Chicken And Vegetables", 300),
                RecipeItem("Berry Blast Acai Bowl", 475),
                RecipeItem("Sweet Potato Cake Lemony Slaw", 269),
                RecipeItem("Honey Butter Chicken Tenders", 500)
            )
        )
    }
}

@Composable
fun RecipeGrid(recipes: List<RecipeItem>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(recipes.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { recipe ->
                    RecipeCard(recipe, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: RecipeItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery), // Placeholder image
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
            Text(text = recipe.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = android.R.drawable.star_big_on), contentDescription = null)
                Text(text = "${recipe.likes}", fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

data class RecipeItem(val name: String, val likes: Int)

@Preview(showBackground = true)
@Composable
fun PreviewMealRecipe() {
    RecipeScreen()
}