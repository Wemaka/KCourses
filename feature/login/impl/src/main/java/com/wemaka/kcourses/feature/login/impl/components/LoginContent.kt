package com.wemaka.kcourses.feature.login.impl.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wemaka.kcourses.uikit.R
import com.wemaka.kcourses.uikit.components.DefaultButton
import com.wemaka.kcourses.uikit.components.PasswordInput
import com.wemaka.kcourses.uikit.components.TextInput
import com.wemaka.kcourses.uikit.space16
import com.wemaka.kcourses.uikit.space24
import com.wemaka.kcourses.uikit.space28
import com.wemaka.kcourses.uikit.space32
import com.wemaka.kcourses.uikit.space4
import com.wemaka.kcourses.uikit.space8
import com.wemaka.kcourses.uikit.theme.Blue
import com.wemaka.kcourses.uikit.theme.KCoursesTheme
import com.wemaka.kcourses.uikit.theme.Orange
import com.wemaka.kcourses.uikit.theme.OrangeLight
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest

@OptIn(FlowPreview::class)
@Composable
internal fun LoginContent(
    isEmailValid: Boolean,
    isButtonEnabled: Boolean,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onLogin: () -> Unit
) {
    val textEmailState = rememberTextFieldState()
    val textPassState = rememberTextFieldState()

    LaunchedEffect(Unit) {
        snapshotFlow { textEmailState.text }
            .collectLatest { onChangeEmail(it.toString()) }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { textPassState.text }
            .collectLatest { onChangePassword(it.toString()) }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(space16)
            .statusBarsPadding(),
        contentAlignment = BiasAlignment(horizontalBias = 0f, verticalBias = -0.3f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = stringResource(R.string.login_title),
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(Modifier.height(space28))

            AuthInputs(
                isEmailValid = isEmailValid,
                emailState = textEmailState,
                passwordState = textPassState
            )

            Spacer(Modifier.height(space24))

            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.btn_login),
                onClick = onLogin,
                isEnabled = isButtonEnabled
            )

            Spacer(Modifier.height(space16))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(space4),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.no_account))
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Medium
                            )
                        ) {
                            append(stringResource(R.string.register_link))
                        }
                    },
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(R.string.forgot_password),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Spacer(Modifier.height(space32))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(space32))

            SocialButtons()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    KCoursesTheme {
        LoginContent(
            false,
            false,
            {},
            {},
            {}
        )
    }
}