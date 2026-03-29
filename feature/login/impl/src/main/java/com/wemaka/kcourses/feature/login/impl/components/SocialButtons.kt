package com.wemaka.kcourses.feature.login.impl.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.wemaka.kcourses.uikit.R
import com.wemaka.kcourses.uikit.components.DefaultButton
import com.wemaka.kcourses.uikit.space16
import com.wemaka.kcourses.uikit.theme.Blue
import com.wemaka.kcourses.uikit.theme.Orange
import com.wemaka.kcourses.uikit.theme.OrangeLight

@Composable
internal fun SocialButtons(
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.spacedBy(space16)
    ) {
        DefaultButton(
            modifier = Modifier.weight(1f),
            text = "",
            onClick = { uriHandler.openUri("https://vk.com/") },
            color = Blue,
            iconRes = R.drawable.ic_wk
        )

        DefaultButton(
            modifier = Modifier.weight(1f),
            text = "",
            onClick = { uriHandler.openUri("https://ok.ru/") },
            gradientBrush = Brush.verticalGradient(
                colors = listOf(OrangeLight, Orange)
            ),
            iconRes = R.drawable.ic_odnok
        )
    }
}