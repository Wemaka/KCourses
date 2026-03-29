package com.wemaka.kcourses.feature.login.impl.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.wemaka.kcourses.uikit.R
import com.wemaka.kcourses.uikit.components.PasswordInput
import com.wemaka.kcourses.uikit.components.TextInput
import com.wemaka.kcourses.uikit.space16
import com.wemaka.kcourses.uikit.space8

@Composable
internal fun AuthInputs(
    isEmailValid: Boolean,
    emailState: TextFieldState,
    passwordState: TextFieldState,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space16)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(space8)
        ) {
            Text(
                text = stringResource(R.string.email_label),
                style = MaterialTheme.typography.titleLarge
            )

            TextInput(
                modifier = Modifier
                    .height(40.dp)
                    .focusRequester(focusRequester),
                state = emailState,
                placeholder = stringResource(R.string.email_placeholder),
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 16.dp
                ),
                isError = emailState.text.isNotEmpty() && !isEmailValid,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                onKeyboardAction = KeyboardActionHandler {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(space8)
        ) {
            Text(
                text = stringResource(R.string.password_label),
                style = MaterialTheme.typography.titleLarge
            )

            PasswordInput(
                state = passwordState,
                placeholder = stringResource(R.string.password_placeholder),
                modifier = Modifier.height(40.dp),
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 16.dp
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                onKeyboardAction = KeyboardActionHandler {
                    focusManager.clearFocus()
                }
            )
        }
    }
}