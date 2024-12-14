package org.nunocky.loginformsample.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * pseudo login function
 */
suspend fun _login(username: String, password: String): User {
    delay(3_000)

    if (username == "abc" && password == "abc123") {
        return User(1, username, "xxxxx-xxxxx-xxxxxx")
    } else {
        throw Exception("Invalid username or password")
    }
}

/**
 * LoginViewModel
 */
class LoginViewModel : ViewModel() {
    private var _state = MutableStateFlow<LoginState>(LoginState.Initial)
    var state = _state.asStateFlow()

    fun processLogin(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = LoginState.Loading

            _state.value = try {
                val user = _login(username, password)
                LoginState.Success(user)
            } catch (e: Exception) {
                LoginState.Error(e)
            }
        }
    }
}