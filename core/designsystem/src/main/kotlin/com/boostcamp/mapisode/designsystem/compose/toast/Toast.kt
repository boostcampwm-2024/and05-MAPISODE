package com.boostcamp.mapisode.designsystem.compose.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.MapisodeTextStyle
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

enum class ToastConstraintType(val value: Dp) {
	HorizontalPadding(24.dp),
	VerticalPadding(16.dp),
	HorizontalMargin(8.dp),
}

@Composable
fun Toast(
	modifier: Modifier = Modifier,
	backgroundColor: Color = MapisodeTheme.colorScheme.chipIconThird,
	contentColor: Color = MapisodeTheme.colorScheme.foreground,
	toastData: ToastData,
) {
	var lineCount by remember { mutableIntStateOf(1) }

	Box(
		modifier = modifier
			.padding(horizontal = ToastConstraintType.HorizontalMargin.value)
			.background(backgroundColor, RoundedCornerShape(8.dp))
			.padding(
				horizontal = ToastConstraintType.HorizontalPadding.value,
				vertical = ToastConstraintType.VerticalPadding.value,
			)
			.fillMaxWidth(),
	) {
		MapisodeText(
			text = toastData.message,
			color = contentColor,
			style = MapisodeTextStyle.Default,
			onTextLayout = {
				lineCount = it.lineCount
			},
			modifier = Modifier.align(
				if (lineCount == 1) {
					Alignment.Center
				} else {
					Alignment.CenterStart
				},
			),
		)
	}
}
