package com.wemaka.kcourses.feature.login.impl

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wemaka.kcourses.common.compose.ObserveAsEvent
import com.wemaka.kcourses.feature.login.impl.components.LoginContent
import com.wemaka.kcourses.feature.login.impl.state.SideEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    onSuccessLogin: () -> Unit
) {
    val viewModel: LoginViewModel = koinViewModel()

    LoginContent(
        isEmailValid = viewModel.isEmailValid.collectAsStateWithLifecycle().value,
        isButtonEnabled = viewModel.isButtonEnabled.collectAsStateWithLifecycle().value,
        onChangeEmail = viewModel::changeEmail,
        onChangePassword = viewModel::changePassword,
        onLogin = viewModel::logIn
    )

    ObserveAsEvent(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            SideEffect.SuccessLogIn -> onSuccessLogin()
            SideEffect.UnsuccessLogIn -> {}
            SideEffect.ServerError -> {}
        }
    }
}
