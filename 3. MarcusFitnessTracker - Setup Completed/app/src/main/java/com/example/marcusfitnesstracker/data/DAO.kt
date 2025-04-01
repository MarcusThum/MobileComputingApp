package com.example.marcusfitnesstracker.data

import androidx.room.Dao
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
