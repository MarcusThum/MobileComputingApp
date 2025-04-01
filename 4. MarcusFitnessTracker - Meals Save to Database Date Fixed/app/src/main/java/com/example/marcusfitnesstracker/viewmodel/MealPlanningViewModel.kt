package com.example.marcusfitnesstracker.viewmodel

import MealRepository
import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marcusfitnesstracker.R
import com.example.marcusfitnesstracker.utils.LocalVariables
import com.example.marcusfitnesstracker.utils.LocalVariables.meals
import java.sql.Types.NULL
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Locale
import kotlin.random.Random
import coil.compose.rememberAsyncImagePainter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.marcusfitnesstracker.api.RetrofitClient
import com.example.marcusfitnesstracker.data.MealDao
import com.example.marcusfitnesstracker.data.MealEntity
import com.example.marcusfitnesstracker.data.MealViewModel
import com.example.marcusfitnesstracker.utils.LocalVariables.recipeList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Month
import java.time.format.DateTimeFormatter

class MealPlanningViewModel : ViewModel() {

    @Composable
    fun MainScreen(mealDao: MealDao) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "mealPlan") {
            composable("mealPlan") {
                MealPlanScreen(navController)
            }
            composable("recipeScreen/{mealType}") { backStackEntry ->
                RecipeScreen(navController, mealDao)
            }
        }
    }

    @Composable
    fun MealPlanScreen(navController: NavHostController) {

        var foodPlannerPageIndex: Int = returnDayIndex(LocalVariables.dayOfWeek)
        var pageIndex by rememberSaveable { mutableStateOf(foodPlannerPageIndex) }
        var selectedDate by rememberSaveable { mutableStateOf(LocalVariables.currentDay) } // Track selected date

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
        {

//            MealCategory("BREAKFAST", listOf(MealItem("Berry Blast Acai Bowl", "8 servings")))
//            MealCategory("LUNCH", listOf(
//                MealItem("Grilled Chicken And Vegetables", "5 servings"),
//                MealItem("Sweet Potato Cake Lemony Slaw", "5 servings")
//            ))
//            MealCategory("DINNER", listOf(MealItem("Honey Butter Chicken Tenders", "8 servings")))
            DateSelectionBar(selectedDate = selectedDate, pageIndex = pageIndex, onDateSelected = { newIndex, newDate ->
                pageIndex = newIndex // Update state to trigger recomposition
                selectedDate = newDate
                LocalVariables.selectedDay = pageIndex
            })

            Spacer(modifier = Modifier.height(16.dp))

            MealDaysArray(
                pageIndex,
                onAddMeal = { mealType ->
                    navController.navigate("recipeScreen/$mealType")
                    LocalVariables.selectedMeal = mealType
                }
            )
        }
    }

    @Composable
    fun MealDaysArray(index: Int, onAddMeal: (String) -> Unit) {
        var meals = LocalVariables.meals
        MealCategory("BREAKFAST", meals[index][0], onAddMeal)
        MealCategory("LUNCH", meals[index][1], onAddMeal)
        MealCategory("DINNER", meals[index][2], onAddMeal)
    }

    @Composable
    fun DateSelectionBar(selectedDate: Int, pageIndex: Int, onDateSelected: (Int, Int) -> Unit) {

        var currentMonth = remember { LocalVariables.currentMonth }
        var currentDay = remember { LocalVariables.currentDay }
        var dayOfWeek = remember { LocalVariables.dayOfWeek }

        // State to ensure the effect runs only once
        var hasLaunched by remember { mutableStateOf(false) }

        // Automatically select current day on first render
        LaunchedEffect(Unit) {
            if (!hasLaunched) {
                // Trigger selection for the current day
                onDateSelected(pageIndex, selectedDate)
                hasLaunched = true  // Set the flag to true after the effect has been executed
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "$currentMonth\n", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            getListOfDates(dayOfWeek, currentDay).forEachIndexed() { index, list ->
                val date = list.split(" ")[1].toInt() // Extract day number
                Text(text = list, fontSize = 14.sp, color = if (date == selectedDate) Color.Green else Color.Gray,
                    modifier = Modifier
                        .clickable{
                            onDateSelected(index, date)
                            currentDay =- index
                        }
                )
            }
        }
    }
    
    fun returnDayIndex(dayOfWeek: String): Int {
        val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        var indexDay: Int = 0
        days.forEachIndexed { index, day ->
            if (dayOfWeek.equals(day, ignoreCase = true)) {
                indexDay = index
            }
        }
        return indexDay
    }

    fun returnMealIndex(selectedMeal: String): Int {
        val meals = listOf("Breakfast", "Lunch", "Dinner")
        var indexMeal: Int = 0
        meals.forEachIndexed { index, meal ->
            if (selectedMeal.equals(meal, ignoreCase = true)) {
                indexMeal = index
            }
        }
        return indexMeal
    }

    fun getListOfDates(dayOfWeek: String, currentDay: Int): List<String> {
        val today = LocalDate.now()
        val currentDate = LocalDate.of(today.year, today.month, currentDay)
        val dayIndex = returnDayIndex(dayOfWeek) // 0 = Sunday

        // Start of the week (Sunday)
        val startOfWeek = currentDate.minusDays(dayIndex.toLong())

        return List(7) { offset ->
            val date = startOfWeek.plusDays(offset.toLong())
            "${date.dayOfWeek.name.take(3).uppercase()} ${date.dayOfMonth}"
        }
    }

//    fun getListOfDates(dayOfWeek: String, currentDay: Int): List<String> {
//
//        val indexDay = returnDayIndex(dayOfWeek)
//
//        if (indexDay != NULL) {
//            val sunday = currentDay - indexDay
//            val monday = currentDay - (indexDay - 1)
//            val tuesday = currentDay - (indexDay - 2)
//            val wednesday = currentDay - (indexDay - 3)
//            val thursday = currentDay - (indexDay - 4)
//            val friday = currentDay - (indexDay - 5)
//            val saturday = currentDay - (indexDay - 6)
//
//            return listOf("SUN $sunday", "MON $monday", "TUE $tuesday", "WED $wednesday", "THU $thursday", "FRI $friday", "SAT $saturday")
//        }else{
//            return listOf()
//        }
//
//    }

    @Composable
//    fun MealCategory(title: String, meals: List<MealItem>) {
//        Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
//            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                items(meals.size) { index ->
//                    MealCard(meals[index])
//                }
//            }
//        }
//    }
    fun MealCategory(title: String, meals: List<MealItem>, onAddMeal: (String) -> Unit) {

        Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_input_add), // Plus icon
                    contentDescription = "Add Meal",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onAddMeal(title) } // Trigger meal addition
                )
            }

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
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberAsyncImagePainter(model = meal.imageUrl),
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
    }

//    @Composable
//    fun MealCard(meal: MealItem) {
//        Card(
//            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
//            shape = MaterialTheme.shapes.medium,
//            colors = CardDefaults.cardColors(containerColor = Color.White)
//        ) {
//            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//                Image(
//                    painter = rememberAsyncImagePainter(model = meal.imageUrl), // Placeholder image
//                    contentDescription = null,
//                    modifier = Modifier.size(50.dp).padding(8.dp),
//                    contentScale = ContentScale.Crop
//                )
//                Column(modifier = Modifier.padding(start = 16.dp)) {
//                    Text(text = meal.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                    Text(text = meal.servings, fontSize = 14.sp, color = Color.Gray)
//                }
//            }
//        }
//    }

    data class MealItem(val name: String, val servings: String, val imageUrl: String)

    @Composable
    fun PreviewFoodPlanner() {
//        MainScreen()
    }

    // Meal Recipe

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RecipeAppBar(navController: NavHostController, title: String) {
        TopAppBar(
            title = {
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) { // Back Button
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { /* Handle menu or settings */ }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                }
            }
        )
    }

    @Composable
    fun RecipeScreen(navController: NavHostController, mealDao: MealDao) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            RecipeAppBar(navController, "Select Recipes") // Add App Bar

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
//            RecipeGrid(
//                listOf(
//                    RecipeItem("Grilled Chicken And Vegetables", 5,300, "https://github.com/MarcusThum/MobileComputingApp/blob/main/recipes/images/Grilled-Chicken-Vegetables.jpg?raw=true"),
//                    RecipeItem("Berry Blast Acai Bowl", 8,475, "https://github.com/MarcusThum/MobileComputingApp/blob/main/recipes/images/berry-blast-acai-bowl.jpg?raw=true"),
//                    RecipeItem("Sweet Potato Cake Lemony Slaw", 6,269, "https://github.com/MarcusThum/MobileComputingApp/blob/main/recipes/images/sweet-potato-style-slaw.jpg?raw=true"),
//                    RecipeItem("Honey Butter Chicken Tenders", 7,500, "https://github.com/MarcusThum/MobileComputingApp/blob/main/recipes/images/Honey-Butter-Chicken-Tenders.jpg?raw=true")
//                ), navController
//            )
            fetchRecipes()
            RecipeGrid(recipeList, navController, mealDao)
        }
    }

    fun fetchRecipes() {
        RetrofitClient.instance.getRecipes().enqueue(object : Callback<LocalVariables.RecipeResponse> {
            override fun onResponse(call: Call<LocalVariables.RecipeResponse>, response: Response<LocalVariables.RecipeResponse>) {
                if (response.isSuccessful) {
                    LocalVariables.recipeList.clear()
                    response.body()?.recipes?.let { recipes ->
                        for (recipe in recipes) {
                            LocalVariables.recipeList.add(RecipeItem(recipe.name, recipe.servings, recipe.likes, recipe.imageUrl))

                        }
                    }
                }
            }

            override fun onFailure(call: Call<LocalVariables.RecipeResponse>, t: Throwable) {
                Log.e("Retrofit", "Error fetching recipes", t)
            }
        })
    }

    // Function to add a recipe to the meal list
    fun addRecipeToMeal(dayIndex: Int, mealTypeIndex: Int, recipe: MealItem) {
        // Adding the clicked recipe to the corresponding meal category
        meals[dayIndex][mealTypeIndex].add(recipe)
    }

    // Example of how to handle a recipe click
    fun onRecipeClick(dayIndex: Int, mealTypeIndex: Int, recipe: MealItem) {
        // Add the recipe to the correct list after a click
        addRecipeToMeal(dayIndex, mealTypeIndex, recipe)
    }

    @Composable
    fun RecipeGrid(recipes: List<RecipeItem>, navController: NavHostController, mealDao: MealDao) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(recipes.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowItems.forEach { recipe ->
                        RecipeCard(recipe, Modifier.weight(1f)) { clickedRecipe ->
                            // Handle the click event for the clicked recipe
                            // For example, navigate to a detailed recipe screen
                            onRecipeClick(LocalVariables.selectedDay, returnMealIndex(LocalVariables.selectedMeal), MealItem(clickedRecipe.name, "${clickedRecipe.servings} servings", clickedRecipe.imageUrl))
                            saveMeals(mealDao, LocalVariables.meals)
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }

    fun saveMeals(mealDao: MealDao, meals: Array<Array<MutableList<MealPlanningViewModel.MealItem>>>) {
        viewModelScope.launch {
            mealDao.clearAll()
            val allItems = mutableListOf<MealEntity>()
            for (day in meals.indices) {
                for (mealIndex in meals[day].indices) {
                    for (item in meals[day][mealIndex]) {
                        allItems.add(item.toEntity(day, mealIndex))
                    }
                }
            }
            mealDao.insertAll(allItems)
        }
    }

    fun MealPlanningViewModel.MealItem.toEntity(day: Int, mealIndex: Int): MealEntity {
        return MealEntity(
            name = this.name,
            servings = this.servings,
            day = day,
            mealIndex = mealIndex,
            imageUrl = this.imageUrl
        )
    }

    fun MealEntity.toUiModel(): MealPlanningViewModel.MealItem {
        return MealPlanningViewModel.MealItem(
            name = this.name,
            servings = this.servings.toString(),
            imageUrl = this.imageUrl
        )
    }

    fun loadMeals(mealDao: MealDao, callback: (Array<Array<MutableList<MealPlanningViewModel.MealItem>>>) -> Unit) {
        viewModelScope.launch {
            val items = mealDao.getAll()
            val loadedMeals = Array(7) { Array(3) { mutableListOf<MealPlanningViewModel.MealItem>() } }
            for (item in items) {
                loadedMeals[item.day][item.mealIndex].add(item.toUiModel())
            }
            callback(loadedMeals)
        }
    }

    @Composable
    fun RecipeCard(recipe: RecipeItem, modifier: Modifier = Modifier, onClick: (RecipeItem) -> Unit) {
        Card(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable { onClick(recipe) }, // Add click listener here
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = rememberAsyncImagePainter(model = recipe.imageUrl), // Placeholder image
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

    data class RecipeItem(val name: String, val servings: Int, val likes: Int, val imageUrl: String)

    @Composable
    fun PreviewMealRecipe() {
//        RecipeScreen(navController = rememberNavController())
    }

}