package org.nunocky.loginformsample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


/**
 * User Info
 */
data class User(
    val userId: Long, val username: String, val access_token: String
)

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
 * LoginState
 */
sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    data class Success(val user: User) : LoginState()
    data class Error(val e: Exception) : LoginState()
}

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

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val viewModel by remember { mutableStateOf(LoginViewModel()) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by viewModel.state.collectAsStateWithLifecycle()

    val message = when (loginState) {
        is LoginState.Initial -> "Please login"
        is LoginState.Loading -> "Loading..."
        is LoginState.Success -> "Login success"
        is LoginState.Error -> "Login failed"
    }

    val message_tag = when (loginState) {
        is LoginState.Initial -> TAG_TEXT_MESAGE
        is LoginState.Loading -> TAG_TEXT_MESAGE_LOADING
        is LoginState.Success -> TAG_TEXT_MESAGE_SUCCESS
        is LoginState.Error -> TAG_TEXT_MESAGE_ERROR
    }

    // 要素は縦方向上から配置、水平方向センタリングする
    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("username")
        TextField(
            value = username,
            onValueChange = { v -> username = v },
            modifier = Modifier.testTag(TAG_USERNAME)
        )

        Text("password", modifier = Modifier.padding(top = 16.dp))
        TextField(
            value = password,
            onValueChange = { v -> password = v },
            modifier = Modifier.testTag(TAG_PASSWORD)
        )

        Button(
            onClick = {
                viewModel.processLogin(username, password)
            }, modifier = Modifier
                .padding(top = 16.dp)
                .testTag(TAG_BTN_LOGIN)
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.padding(top = 16.dp))

        if (loginState is LoginState.Loading) {
            CircularProgressIndicator()
        }

        Text(message, modifier = Modifier.testTag(message_tag))
    }
}