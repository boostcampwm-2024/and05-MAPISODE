package com.boostcamp.mapisode.episode

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.boostcamp.mapisode.designsystem.compose.MapisodeDialog

@Composable
internal fun ClearDialog(
	onResultRequest: (Boolean) -> Unit,
	onDismissRequest: () -> Unit,
) {
	MapisodeDialog(
		onResultRequest = onResultRequest,
		onDismissRequest = onDismissRequest,
		titleText = stringResource(R.string.new_episode_clear_dialog_title),
		contentText = stringResource(R.string.new_episode_clear_dialog_content),
		cancelText = stringResource(R.string.new_episode_clear_dialog_cancel),
		confirmText = stringResource(R.string.new_episode_clear_dialog_confirm),
	)
}
