package com.example.marcusfitnesstracker.di


import MealRepository
import UserSetupRepository
import android.app.Application
import androidx.room.Room
import com.example.marcusfitnesstracker.data.AppDatabase
import com.example.marcusfitnesstracker.data.MealViewModel
import com.example.marcusfitnesstracker.data.UserSetupViewModel
import com.example.marcusfitnesstracker.viewmodel.AppBarViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.marcusfitnesstracker.viewmodel.FitnessViewModel
import com.example.marcusfitnesstracker.viewmodel.MealPlanningViewModel
import com.example.marcusfitnesstracker.viewmodel.SetupScreenViewModel
import com.example.marcusfitnesstracker.viewmodel.SleepPatternViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.scope.get


val appModule = module {

    // Room DB
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    // DAO
    single { get<AppDatabase>().userSetupDao() }
    single { get<AppDatabase>().mealDao() }
    single { get<AppDatabase>().sleepScheduleDao() }

    // Repository
    single { UserSetupRepository(get()) }
    single { MealRepository(get()) }
    single { MealViewModel(get()) }
    single { com.example.marcusfitnesstracker.data.SleepPatternViewModel(get()) }


    // ViewModel
    viewModel { UserSetupViewModel(get()) }
    viewModel { MealViewModel(get()) }
    viewModel { com.example.marcusfitnesstracker.data.SleepPatternViewModel(get()) }


    viewModel { FitnessViewModel() }
    viewModel { MealPlanningViewModel() }
    viewModel { AppBarViewModel(get(), get()) }
    viewModel { SleepPatternViewModel() }
    viewModel { SetupScreenViewModel() }


}