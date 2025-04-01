package com.example.marcusfitnesstracker.data

import UserSetupRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marcusfitnesstracker.viewmodel.MealPlanningViewModel
import kotlinx.coroutines.launch

class UserSetupViewModel(private val repository: UserSetupRepository) : ViewModel() {

    private val _userSetup = MutableLiveData<UserSetup?>()
    val userSetup: LiveData<UserSetup?> get() = _userSetup

    fun saveUserSetup(setup: UserSetup) {
        viewModelScope.launch {
            repository.saveSetup(setup)
            _userSetup.postValue(setup)
        }
    }

    fun loadUserSetup() {
        viewModelScope.launch {
            val setup = repository.getSetup()
            _userSetup.postValue(setup)
        }
    }
}

class MealViewModel(private val mealDao: MealDao) : ViewModel() {

    fun saveMeals(meals: Array<Array<MutableList<MealPlanningViewModel.MealItem>>>) {
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

    fun loadMeals(callback: (Array<Array<MutableList<MealPlanningViewModel.MealItem>>>) -> Unit) {
        viewModelScope.launch {
            val items = mealDao.getAll()
            val loadedMeals = Array(7) { Array(3) { mutableListOf<MealPlanningViewModel.MealItem>() } }
            for (item in items) {
                loadedMeals[item.day][item.mealIndex].add(item.toUiModel())
            }
            callback(loadedMeals)
        }
    }
}