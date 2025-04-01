package com.example.marcusfitnesstracker


import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.marcusfitnesstracker.data.UserSetup
import com.example.marcusfitnesstracker.data.UserSetupViewModel
import com.example.marcusfitnesstracker.utils.LocalVariables
import com.example.marcusfitnesstracker.viewmodel.AppBarViewModel
import com.example.marcusfitnesstracker.viewmodel.FitnessViewModel
import com.example.marcusfitnesstracker.viewmodel.MealPlanningViewModel
import com.example.marcusfitnesstracker.viewmodel.SetupScreenViewModel
import kotlinx.coroutines.withTimeout
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val fitnessViewModel: FitnessViewModel by viewModel()
    private val mealPlanningViewModel: MealPlanningViewModel by viewModel()
    private val appBarViewModel: AppBarViewModel by viewModel()
    private val sleepPatternViewModel: AppBarViewModel by viewModel()
    private val setupScreenViewModel: SetupScreenViewModel by viewModel()
    private val userSetupViewModel: UserSetupViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var setupCompleted by remember { mutableStateOf(false) }
            var startSetup by remember { mutableStateOf(false) }
            var isLoading by remember { mutableStateOf(true) }
            var setupDone by remember { mutableStateOf(false) }

            // Load setup data
            userSetupViewModel.loadUserSetup()

            userSetupViewModel.userSetup.observe(this) { setup ->

                if (setup == null || !setup.setupCompleted) {
                    isLoading = false
                } else {
                    setup?.let {
                        val steps = it.targetSteps
                        val age = it.age
                        val height = it.height
                        val weight = it.weight
                        val gender = it.gender
                        val completed = it.setupCompleted


                        if(completed) {
                            LocalVariables.targetSteps = steps.toInt()
                            LocalVariables.height = height
                            LocalVariables.weight = weight
                            LocalVariables.age = age
                            LocalVariables.gender = gender
                            setupCompleted = true
                            startSetup = true
                            isLoading = false
                        }else{
                            isLoading = false
                            setupCompleted = false
                            startSetup = false
                        }
                        Log.d("SetupData", "Steps: $steps, Age: $age, Height: $height, Weight: $weight, Gender: $gender, Done: $completed")
                    }
                }
            }

            if(!isLoading) {
                if (!startSetup) {
                    appBarViewModel.StartSetup(startSetup = { startSetup = true })
                }else{
                    if (!setupCompleted) {
                        appBarViewModel.SetupScreen(
                            onSetupComplete = { targetSteps, age, height, gender, weight ->
                                setupCompleted = true
                                val setup = UserSetup(
                                    id = 0,
                                    targetSteps = targetSteps,
                                    height = height,
                                    weight = weight,
                                    age = age,
                                    gender = gender,
                                    setupCompleted = true
                                )
                                LocalVariables.targetSteps = targetSteps.toInt()
                                LocalVariables.height = height
                                LocalVariables.weight = weight
                                LocalVariables.age = age
                                LocalVariables.gender = gender
                                userSetupViewModel.saveUserSetup(setup)
                            }
                        )

                    } else {
                        appBarViewModel.AppBar()
                    }
                }
            }

        }
    }
}