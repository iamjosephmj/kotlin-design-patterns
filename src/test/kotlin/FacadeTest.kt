import main.kotlin.Database
import main.kotlin.Repository
import main.kotlin.User
import org.junit.Test

class FacadeTest {

    companion object {
        const val USER_ID = "T/1532/019"
    }

    @Test
    fun `facade user id test`() {
        // Mock database
        val database = Database("Bret-DB")

        val repository = Repository(database)

        val user = User(USER_ID)

        repository.storeUser(user = user)

        val userId = repository.fetchUser()

        assert(USER_ID == userId)
    }
}