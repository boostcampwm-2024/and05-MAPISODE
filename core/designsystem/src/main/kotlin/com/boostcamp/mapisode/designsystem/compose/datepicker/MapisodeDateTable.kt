package com.boostcamp.mapisode.designsystem.compose.datepicker

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MapisodeDateTable(
	selectedMonth: YearMonth,
	selectedDate: LocalDate,
	onDateSelect: (LocalDate) -> Unit
) {
	// 해당 월의 일 수
	val daysInMonth = selectedMonth.lengthOfMonth()
	// 1일이 무슨 요일인지
	val firstDayOfMonth = selectedMonth.atDay(1).dayOfWeek.value % 7

	val dateList = mutableListOf<LocalDate?>()

	// 1일 이전은 빈 칸 null 처리
	for (i in 0 until firstDayOfMonth) {
		dateList.add(null)
	}
	for (day in 1..daysInMonth) {
		dateList.add(selectedMonth.atDay(day))
	}

	val finalDateList = dateList.toList()

	LazyVerticalGrid(
		columns = GridCells.Fixed(7),
		contentPadding = PaddingValues(vertical = 8.dp)
	) {
		items(finalDateList) { date ->
			date?.let {
				MapisodeDateBox(
					date = it,
					isSelected = it == selectedDate,
					isSunday = it.dayOfWeek.value == 7,
					onSelect = { onDateSelect(it) }
				)
			} ?: Spacer(modifier = Modifier.size(36.dp))
		}
	}
}
