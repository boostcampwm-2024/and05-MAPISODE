package com.boostcamp.mapisode.home.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.home.R

enum class ChipType(
	@StringRes val textResId: Int,
	@DrawableRes val iconResId: Int,
) {
	EATERY(R.string.home_chip_eat, R.drawable.ic_eat),
	SEEING(R.string.home_chip_see, R.drawable.ic_see),
	OTHER(R.string.home_chip_other, R.drawable.ic_other),
}

@Composable
fun getChipIconTint(chipType: ChipType): Color = when (chipType) {
	ChipType.EATERY -> MapisodeTheme.colorScheme.eatIconColor
	ChipType.SEEING -> MapisodeTheme.colorScheme.seeIconColor
	ChipType.OTHER -> MapisodeTheme.colorScheme.otherIconColor
}
