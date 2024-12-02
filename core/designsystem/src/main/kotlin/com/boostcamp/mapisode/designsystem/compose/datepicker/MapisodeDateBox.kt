package com.boostcamp.mapisode.designsystem.compose.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.TextAlignment
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import java.time.LocalDate

@Composable
fun MapisodeDateBox(
	date: LocalDate,
	isSelected: Boolean,
	isSunday: Boolean,
	onSelect: () -> Unit,
) {
	Box(
		modifier = Modifier
			.size(36.dp)
			.clip(RoundedCornerShape(4.dp))
			.background(
				if (isSelected) {
					MapisodeTheme.colorScheme.dateBoxSelectedBackground
				} else {
					Color.Transparent
				},
			)
			.clickable(onClick = onSelect),
		contentAlignment = Alignment.Center,
	) {
		MapisodeText(
			text = date.dayOfMonth.toString(),
			color = when {
				isSelected -> MapisodeTheme.colorScheme.dateBoxSelectedText
				date == LocalDate.now() -> MapisodeTheme.colorScheme.dateBoxTodayText
				isSunday -> MapisodeTheme.colorScheme.dateBoxSundayText
				else -> MapisodeTheme.colorScheme.dateBoxUnselectedText
			},
			modifier = Modifier
				.fillMaxWidth()
				.clip(RoundedCornerShape(4.dp))
				.padding(vertical = 8.dp),
			textAlignment = TextAlignment.Center,
		)
	}
}
