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
	modifier: Modifier = Modifier,
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
		) {
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp),
			) {
				MapisodeText(titleText, style = MapisodeTheme.typography.titleLarge)

				MapisodeText(contentText)

				Spacer(modifier = Modifier.height(16.dp))

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
						)
					)
					MapisodeText(
						text = confirmText,
						modifier = Modifier.clickable(
							enabled = true,
							onClick = {
								onResultRequest(false)
								onDismissRequest()
							},
							indication = MapisodeRippleBIndication,
							interactionSource = remember { MutableInteractionSource() },
						)
					)
				}
			}
		}
	}

}
