package com.example.marcusfitnesstracker.di


import UserSetupRepository
import android.app.Application
import androidx.room.Room
import com.example.marcusfitnesstracker.data.AppDatabase
import com.example.marcusfitnesstracker.data.UserSetupViewModel
import com.example.marcusfitnesstracker.viewmodel.AppBarViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.marcusfitnesstracker.viewmodel.FitnessViewModel
import com.example.marcusfitnesstracker.viewmodel.MealPlanningViewModel
import com.example.marcusfitnesstracker.viewmodel.SetupScreenViewModel
import com.example.marcusfitnesstracker.viewmodel.SleepPatternViewModel
import org.koin.android.ext.koin.androidApplication


val appModule = module {

    // Room DB
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    // DAO
    single { get<AppDatabase>().userSetupDao() }

    // Repository
    single { UserSetupRepository(get()) }

    // ViewModel
    viewModel { UserSetupViewModel(get()) }

    viewModel { FitnessViewModel() }
    viewModel { MealPlanningViewModel() }
    viewModel { AppBarViewModel() }
    viewModel { SleepPatternViewModel() }
    viewModel { SetupScreenViewModel() }


}