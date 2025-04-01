import com.example.marcusfitnesstracker.data.UserSetup
import com.example.marcusfitnesstracker.data.UserSetupDao
import kotlinx.coroutines.flow.Flow

class UserSetupRepository(private val dao: UserSetupDao) {
    suspend fun saveSetup(userSetup: UserSetup) = dao.insert(userSetup)
    suspend fun getSetup(): UserSetup? = dao.getUserSetup()
}