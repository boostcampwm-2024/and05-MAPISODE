package com.boostcamp.mapisode.designsystem.compose.datepicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MapisodeDateSelector(
	initialDate: LocalDate = LocalDate.now(),
	onDateSelected: (LocalDate) -> Unit,
) {
	var selectedMonth by remember { mutableStateOf(YearMonth.from(initialDate)) }
	var selectedDate by remember { mutableStateOf(initialDate) }

	Column(
		modifier = Modifier
			.fillMaxWidth(),
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
		) {
			MapisodeIconButton(
				onClick = {
					selectedMonth = selectedMonth.minusMonths(1)
				},
				backgroundColor = Color.Transparent,
			) {
				MapisodeIcon(
					id = R.drawable.ic_arrow_back_ios,
					iconSize = IconSize.Small,
				)
			}

			MapisodeText(
				text = "${selectedMonth.month} ${selectedMonth.year}",
				style = MapisodeTheme.typography.labelLarge,
			)

			MapisodeIconButton(
				onClick = {
					selectedMonth = selectedMonth.plusMonths(1)
				},
				backgroundColor = Color.Transparent,
			) {
				MapisodeIcon(
					id = R.drawable.ic_arrow_forward_ios,
					iconSize = IconSize.Small,
				)
			}
		}

		Spacer(modifier = Modifier.height(8.dp))

		Row(
			modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
		) {
			persistentListOf("월", "화", "수", "목", "금", "토", "일").forEach { day ->
				MapisodeText(
					text = day,
					style = MapisodeTheme.typography.labelLarge,
					color = MapisodeTheme.colorScheme.dayOfWeekText,
				)
			}
		}

		Spacer(modifier = Modifier.height(8.dp))

		MapisodeDateTable(
			selectedMonth = selectedMonth,
			selectedDate = selectedDate,
			onDateSelect = {
				selectedDate = it
				onDateSelected(it)
			},
		)
	}
}
