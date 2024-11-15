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
fun MapisodeOutlinedButton(
	modifier: Modifier = Modifier,
	onClick: () -> Unit,
	text: String,
	enabled: Boolean = true,
	@DrawableRes leftIcon: Int? = null,
	@DrawableRes rightIcon: Int? = null,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
	MapisodeButton(
        onClick = onClick,
        backgroundColors = MapisodeTheme.colorScheme.outlineButtonBackground,
        contentColor = MapisodeTheme.colorScheme.outlineButtonContent,
        modifier = Modifier.width(320.dp).height(40.dp).then(modifier),
        enabled = enabled,
        showBorder = true,
        borderColor = MapisodeTheme.colorScheme.outlineButtonStroke,
        interactionSource = interactionSource,
        rounding = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
		leftIcon?.let { icon ->
			MapisodeIcon(
                id = icon,
                iconSize = IconSize.Small,
            )
			Spacer(modifier = Modifier.width(8.dp))
		}

		MapisodeText(
            text = text,
            color = MapisodeTheme.colorScheme.outlineButtonContent,
            style = MapisodeTheme.typography.labelLarge,
        )

		leftIcon ?: rightIcon?.let { icon ->
			Spacer(modifier = Modifier.width(8.dp))
			MapisodeIcon(
                id = icon,
                iconSize = IconSize.Small,
            )
		}
	}
}

@Preview(showBackground = true)
@Composable
fun MapisodeOutlinedButtonPreview() {
	Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
		MapisodeOutlinedButton(
            onClick = { },
            text = "아웃라인 버튼",
        )
	}
}
