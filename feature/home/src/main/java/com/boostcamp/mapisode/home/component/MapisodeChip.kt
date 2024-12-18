package com.boostcamp.mapisode.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.Surface
import com.boostcamp.mapisode.designsystem.theme.AppTypography
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodeChip(
	text: String,
	@DrawableRes iconId: Int,
	iconTint: Color,
	modifier: Modifier = Modifier,
	onClick: (() -> Unit)? = null,
	isSelected: Boolean = false,
) {
	val backgroundColor = if (isSelected) {
		MapisodeTheme.colorScheme.chipSelectedBackground
	} else {
		MapisodeTheme.colorScheme.chipUnselectedBackground
	}
	val strokeColor = if (isSelected) {
		MapisodeTheme.colorScheme.chipSelectedStroke
	} else {
		MapisodeTheme.colorScheme.chipUnselectedStroke
	}

	val chipModifier = modifier
		.clip(RoundedCornerShape(16.dp))
		.then(
			if (onClick != null) {
				Modifier.clickable(onClick = onClick)
			} else {
				Modifier
			},
		)

	Surface(
		modifier = chipModifier,
		shape = RoundedCornerShape(16.dp),
		color = backgroundColor,
		border = BorderStroke(1.dp, strokeColor),
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.padding(start = 6.dp, end = 9.dp, top = 6.dp, bottom = 6.dp),
		) {
			MapisodeIcon(
				id = iconId,
				iconSize = IconSize.EExtraSmall,
				tint = iconTint,
				modifier = Modifier.padding(4.dp),
			)
			Spacer(modifier = Modifier.width(2.dp))
			MapisodeText(
				text = text,
				color = MapisodeTheme.colorScheme.chipText,
				style = if (onClick != null) AppTypography.titleSmall else AppTypography.labelMedium,
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun MapisodeChipSelectedPreview() {
	MapisodeChip(
		text = "먹거리",
		iconId = R.drawable.ic_eat,
		iconTint = MapisodeTheme.colorScheme.eatIconColor,
		onClick = {},
		isSelected = true,
	)
}

@Preview(showBackground = true)
@Composable
fun MapisodeChipUnselectedPreview() {
	MapisodeChip(
		text = "먹거리",
		iconId = R.drawable.ic_eat,
		iconTint = MapisodeTheme.colorScheme.eatIconColor,
		onClick = {},
	)
}
