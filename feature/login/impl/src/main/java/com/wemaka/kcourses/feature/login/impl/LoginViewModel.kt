package com.wemaka.kcourses.feature.login.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wemaka.kcourses.data.models.users.User
import com.wemaka.kcourses.domain.auth.LoginUseCase
import com.wemaka.kcourses.feature.login.impl.state.SideEffect
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _sideEffect = Channel<SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _emailState = MutableStateFlow("")
    val emailState = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow("")
    val passwordState = _passwordState.asStateFlow()

    @OptIn(FlowPreview::class)
    val isEmailValid = _emailState
        .debounce(200)
        .map {
            it.matches(EmailRegex) && !it.containsCyrillic() && it.isNotEmpty()
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    @OptIn(FlowPreview::class)
    val isButtonEnabled = isEmailValid
        .combine(_passwordState) { emailValid, password ->
            emailValid && password.isNotEmpty()
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun changeEmail(value: String) {
        _emailState.update { value }
    }

    fun changePassword(value: String) {
        _passwordState.update { value }
    }

    fun logIn() {
        viewModelScope.launch {
            loginUseCase.invoke(
                User(
                    id = "1",
                    email = emailState.value
                )
            )

            _sideEffect.send(SideEffect.SuccessLogIn)
        }
    }

    companion object {
        private val EmailRegex = Regex("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")

        private fun String.containsCyrillic(): Boolean {
            return Regex("[\\p{IsCyrillic}]").containsMatchIn(this)
        }
    }
}