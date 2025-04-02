package com.example.marcusfitnesstracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSetupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userSetup: UserSetup)

    @Query("SELECT * FROM user_setup WHERE id = 0 LIMIT 1")
    suspend fun getUserSetup(): UserSetup?
}

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mealItems: List<MealEntity>)

    @Query("DELETE FROM meal_items")
    suspend fun clearAll()

    @Query("SELECT * FROM meal_items")
    suspend fun getAll(): List<MealEntity>
}


@Dao
interface SleepScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: SleepScheduleEntity)

    @Delete
    suspend fun delete(schedule: SleepScheduleEntity)

    @Query("SELECT * FROM sleep_schedule ORDER BY id DESC")
    fun getAllSchedules(): Flow<List<SleepScheduleEntity>>
}