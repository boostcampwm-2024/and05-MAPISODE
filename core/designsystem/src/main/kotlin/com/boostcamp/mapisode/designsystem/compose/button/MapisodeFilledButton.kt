package com.boostcamp.mapisode.designsystem.compose.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodeFilledButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes leftIcon: Int? = null,
    @DrawableRes rightIcon: Int? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    MapisodeButton(
        onClick = onClick,
        backgroundColors = if (enabled) {
            MapisodeTheme.colorScheme.filledButtonEnableBackground
        } else {
            MapisodeTheme.colorScheme.filledButtonDisableBackground
        },
        contentColor = MapisodeTheme.colorScheme.filledButtonContent,
        modifier = Modifier
            .width(320.dp)
            .height(52.dp)
            .then(modifier),
        enabled = enabled,
        showBorder = false,
        interactionSource = interactionSource,
        rounding = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        leftIcon?.let { icon ->
            MapisodeIcon(
                id = icon,
                iconSize = IconSize.Medium
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        MapisodeText(
            text = text,
            color = MapisodeTheme.colorScheme.filledButtonContent,
            style = MapisodeTheme.typography.titleLarge
        )

        leftIcon ?: rightIcon?.let { icon ->
            Spacer(modifier = Modifier.width(8.dp))
            MapisodeIcon(
                id = icon,
                iconSize = IconSize.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapisodeFilledButtonPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MapisodeFilledButton(
            onClick = { },
            text = "활성화 버튼"
        )

        MapisodeFilledButton(
            onClick = { },
            text = "비활성화 버튼",
            enabled = false
        )
    }
}
