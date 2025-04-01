package com.example.marcusfitnesstracker.api

import com.example.marcusfitnesstracker.utils.LocalVariables
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("recipes/recipes.json") // Replace with actual URL
    fun getRecipes(): Call<LocalVariables.RecipeResponse>
}