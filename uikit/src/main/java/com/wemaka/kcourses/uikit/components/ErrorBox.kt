package com.wemaka.kcourses.uikit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wemaka.kcourses.uikit.R
import com.wemaka.kcourses.uikit.TextStyles
import com.wemaka.kcourses.uikit.space12
import com.wemaka.kcourses.uikit.space24
import com.wemaka.kcourses.uikit.theme.KCoursesTheme

@Composable
fun ErrorBox(
    description: String,
    modifier: Modifier = Modifier,
    title: String? = null,
    icon: @Composable (ColumnScope.() -> Unit)? = null,
    button: @Composable (ColumnScope.() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon?.let {
            it(this)

            Spacer(Modifier.height(space12))
        }

        title?.let {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        button?.let {
            Spacer(Modifier.height(space24))

            it(this)
        }
    }
}

@Preview
@Composable
fun PreviewErrorBox() {
    KCoursesTheme(darkTheme = true) {
        ErrorBox(
            title = "Error",
            description = "Sorry",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_settings_applications_24),
                    contentDescription = null,
                    modifier = Modifier.size(140.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            button = {
                DefaultButton(
                    onClick = {},
                    text = "update",
                )
            }
        )
    }
}