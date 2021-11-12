package main.kotlin


sealed class AuthorizationState

object Unauthorized : AuthorizationState()

class Authorized(val username: String) : AuthorizationState()


class AuthorizationPresenter {
    private var state: AuthorizationState = Unauthorized

    val getAccessToken: String
        get() = when (state) {
            is Authorized -> "<Some token>"
            is Unauthorized -> throw IllegalArgumentException()
        }
    fun loginUser(username: String) {
        state = Authorized(username)
    }

    fun logoutUser() {
        state = Unauthorized
    }
}