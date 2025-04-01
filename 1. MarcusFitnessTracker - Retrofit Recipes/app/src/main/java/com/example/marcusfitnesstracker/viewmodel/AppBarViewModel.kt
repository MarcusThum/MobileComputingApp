package com.example.marcusfitnesstracker.viewmodel

import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marcusfitnesstracker.MainActivity
import com.example.marcusfitnesstracker.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random
import com.example.marcusfitnesstracker.viewmodel.FitnessViewModel

class AppBarViewModel : ViewModel() {
    @Composable
    fun AppBar() {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "Fitness",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("Fitness") { HomeScreen() }
                composable("Meal") { SearchScreen() }
                composable("Sleep") { ProfileScreen() }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        val items = listOf("Fitness", "Meal", "Sleep")
        val currentDestination = navController.currentDestination?.route

        NavigationBar {
            items.forEach { screen ->
                NavigationBarItem(
                    icon = {}, // Add icons if needed
                    label = { Text(screen.capitalize()) },
                    selected = currentDestination == screen,
                    onClick = {
                        navController.navigate(screen) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun HomeScreen(viewModel: FitnessViewModel = viewModel()) {
        viewModel.HealthTrackerUI()
    }

    @Composable
    fun SearchScreen(viewModel: MealPlanningViewModel = viewModel()) {
        viewModel.MainScreen()
    }

    @Composable
    fun ProfileScreen() {
        Text("Profile Screen")
    }

}