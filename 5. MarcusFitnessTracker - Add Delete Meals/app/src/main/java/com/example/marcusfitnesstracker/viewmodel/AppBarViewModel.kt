package com.example.marcusfitnesstracker.viewmodel

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marcusfitnesstracker.data.MealDao
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class AppBarViewModel(private val mealDao: MealDao) : ViewModel() {
//    @Composable
//    fun AppBar() {
//        val navController = rememberNavController()
//        val systemUiController = rememberSystemUiController()
//        systemUiController.isStatusBarVisible = false
//
//        Scaffold(
//            bottomBar = { BottomNavigationBar(navController) }
//        ) { innerPadding ->
//            NavHost(
//                navController = navController,
//                startDestination = "Fitness",
//                modifier = Modifier.padding(innerPadding)
//            ) {
//                composable("Fitness") { HomeScreen() }
//                composable("Meal") { SearchScreen() }
//                composable("Sleep") { ProfileScreen() }
//            }
//        }
//    }

    @Composable
    fun AppBar() {

        val navController = rememberNavController()
        val systemUiController = rememberSystemUiController()
        systemUiController.isStatusBarVisible = false

        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "Fitness",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("Fitness") { HomeScreen(navController = navController) }
                composable("Meal") { SearchScreen(mealDao = mealDao) }
//                composable("Sleep") { ProfileScreen() }
                composable("heartRateDetail") { HeartRateDetailScreen(navController = navController) }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        val items = listOf("Fitness", "Meal")
        val currentDestination = navController.currentDestination?.route

        NavigationBar() {
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
    fun HomeScreen(viewModel: FitnessViewModel = viewModel(), navController: NavController) {
        viewModel.HealthTrackerUI(navController)
    }

    @Composable
    fun HeartRateDetailScreen(viewModel: FitnessViewModel = viewModel(), navController: NavController) {
        viewModel.HeartRateDetailScreen(navController)
    }

    @Composable
    fun SearchScreen(viewModel: MealPlanningViewModel = viewModel(), mealDao: MealDao) {
        viewModel.MainScreen(mealDao)
    }

    @Composable
    fun ProfileScreen(viewModel: SleepPatternViewModel = viewModel()) {
        viewModel.SleepQualityChartScreen()
    }

    @Composable
    fun StartSetup(viewModel: SetupScreenViewModel = viewModel(), startSetup: () -> Unit) {
        viewModel.StartSetup(startSetup)
    }

    @Composable
    fun SetupScreen(viewModel: SetupScreenViewModel = viewModel(), onSetupComplete: (Float, Float, Float, String, Float) -> Unit) {
        viewModel.SetupScreen(onSetupComplete)
    }

}