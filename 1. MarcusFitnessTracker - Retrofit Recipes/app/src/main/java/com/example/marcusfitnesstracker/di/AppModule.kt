package com.example.marcusfitnesstracker.di


import com.example.marcusfitnesstracker.viewmodel.AppBarViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.marcusfitnesstracker.viewmodel.FitnessViewModel
import com.example.marcusfitnesstracker.viewmodel.MealPlanningViewModel

val appModule = module {
    viewModel { FitnessViewModel() }
    viewModel { MealPlanningViewModel() }
    viewModel { AppBarViewModel() }

}