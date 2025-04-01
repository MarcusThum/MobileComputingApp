import com.example.marcusfitnesstracker.data.MealDao
import com.example.marcusfitnesstracker.data.MealEntity
import com.example.marcusfitnesstracker.data.UserSetup
import com.example.marcusfitnesstracker.data.UserSetupDao
import kotlinx.coroutines.flow.Flow

class UserSetupRepository(private val dao: UserSetupDao) {
    suspend fun saveSetup(userSetup: UserSetup) = dao.insert(userSetup)
    suspend fun getSetup(): UserSetup? = dao.getUserSetup()
}

class MealRepository(private val mealDao: MealDao) {

    suspend fun getMeals(): List<MealEntity> = mealDao.getAll()

    suspend fun saveMeals(meals: List<MealEntity>) {
        mealDao.clearAll()
        mealDao.insertAll(meals)
    }
}