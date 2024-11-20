package com.boostcamp.mapisode.designsystem.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.boostcamp.mapisode.designsystem.compose.ripple.MapisodeRippleBIndication
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodeDialog(
	onResultRequest: (Boolean) -> Unit,
	onDismissRequest: () -> Unit,
	titleText: String,
	contentText: String,
	cancelText: String,
	confirmText: String,
) {
	Dialog(
		onDismissRequest = {
			onDismissRequest()
		},
		properties = DialogProperties(
			dismissOnBackPress = true,
			dismissOnClickOutside = false,
			securePolicy = SecureFlagPolicy.Inherit,
		),
	) {
		Surface(
			color = MapisodeTheme.colorScheme.dialogBackground,
			rounding = 8.dp,
		) {
			Column(modifier = Modifier.padding(24.dp)) {
				MapisodeText(titleText, style = MapisodeTheme.typography.headlineSmall)

				Spacer(modifier = Modifier.height(16.dp))

				MapisodeText(contentText, style = MapisodeTheme.typography.titleMedium)

				Spacer(modifier = Modifier.height(20.dp))

				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.End,
				) {
					MapisodeText(
						text = cancelText,
						modifier = Modifier.clickable(
							enabled = true,
							onClick = {
								onResultRequest(false)
								onDismissRequest()
							},
							indication = MapisodeRippleBIndication,
							interactionSource = remember { MutableInteractionSource() },
						),
						color = MapisodeTheme.colorScheme.dialogDismiss,
						style = MapisodeTheme.typography.labelLarge,
					)
					Spacer(modifier = Modifier.width(24.dp))
					MapisodeText(
						text = confirmText,
						modifier = Modifier.clickable(
							enabled = true,
							onClick = {
								onResultRequest(true)
								onDismissRequest()
							},
							indication = MapisodeRippleBIndication,
							interactionSource = remember { MutableInteractionSource() },
						),
						color = MapisodeTheme.colorScheme.dialogConfirm,
						style = MapisodeTheme.typography.labelLarge,
					)
				}
			}
		}
	}

}
