import main.kotlin.AuthorizationPresenter
import org.junit.Test

class StateTest {
    @Test
    fun testState() {
        val authorizationPresenter = AuthorizationPresenter()

        authorizationPresenter.loginUser("admin")
        println(authorizationPresenter)
        assert(authorizationPresenter.getAccessToken == "<Some token>")

        authorizationPresenter.logoutUser()
        try {
            authorizationPresenter.getAccessToken
            assert(false)
        } catch (ex: IllegalArgumentException) {
            assert(true)
        }
    }
}