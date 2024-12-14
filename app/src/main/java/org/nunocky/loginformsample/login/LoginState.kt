package org.nunocky.loginformsample.login

/**
 * LoginState
 */
sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    data class Success(val user: User) : LoginState()
    data class Error(val e: Exception) : LoginState()
}