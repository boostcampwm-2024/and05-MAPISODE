package com.boostcamp.mapisode.designsystem.compose.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.ripple.MapisodeRippleBIndication
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import java.time.LocalDate

@Composable
fun MapisodeDatePicker(
	onDismissRequest: () -> Unit,
	onDateSelected: (LocalDate) -> Unit,
	initialDate: LocalDate = LocalDate.now(),
) {
	var selectedDate by remember { mutableStateOf(initialDate) }

	Dialog(
		onDismissRequest = {
			onDismissRequest()
		},
		properties = DialogProperties(
			securePolicy = SecureFlagPolicy.Inherit,
		),
	) {
		Box(
			modifier = Modifier
				.height(360.dp)
				.clip(RoundedCornerShape(16.dp))
				.background(MapisodeTheme.colorScheme.datePickerBackground)
				.padding(horizontal = 20.dp, vertical = 10.dp)
				.padding(bottom = 10.dp),
		) {
			Column(
				modifier = Modifier.fillMaxSize(),
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				MapisodeDateSelector(
					initialDate = initialDate,
					onDateSelected = {
						selectedDate = it
					},
				)
			}

			Row(
				modifier = Modifier
					.fillMaxWidth()
					.align(Alignment.BottomEnd),
				horizontalArrangement = Arrangement.End,
			) {
				MapisodeText(
					text = "취소",
					modifier = Modifier.clickable(
						enabled = true,
						onClick = {
							onDismissRequest()
						},
						indication = MapisodeRippleBIndication,
						interactionSource = remember { MutableInteractionSource() },
					),
					color = MapisodeTheme.colorScheme.datePickerDismiss,
					style = MapisodeTheme.typography.labelLarge,
				)
				Spacer(modifier = Modifier.width(24.dp))
				MapisodeText(
					text = "선택",
					modifier = Modifier.clickable(
						enabled = true,
						onClick = {
							onDateSelected(selectedDate)
							onDismissRequest()
						},
						indication = MapisodeRippleBIndication,
						interactionSource = remember { MutableInteractionSource() },
					),
					color = MapisodeTheme.colorScheme.datePickerRequest,
					style = MapisodeTheme.typography.labelLarge,
				)
			}
		}
	}
}
