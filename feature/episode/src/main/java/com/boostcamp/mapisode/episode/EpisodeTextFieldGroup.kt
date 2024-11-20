package com.boostcamp.mapisode.episode

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.MapisodeTextField
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.textFieldModifier
import com.boostcamp.mapisode.episode.common.NewEpisodeConstant.textFieldVerticalArrangement

@Composable
fun EpisodeTextFieldGroup(
	modifier: Modifier = Modifier,
	@StringRes labelRes: Int,
	@StringRes placeholderRes: Int,
	value: String = "",
	onValueChange: (String) -> Unit = { },
	readOnly: Boolean = false,
	trailingIcon: @Composable (() -> Unit)? = null,
	onTrailingIconClick: (() -> Unit)? = null,
	isError: Boolean = false,
	onSubmitInput: (String) -> Unit = {},
) {
	Column(textFieldModifier, verticalArrangement = textFieldVerticalArrangement) {
		MapisodeText(
			text = stringResource(labelRes),
			style = MapisodeTheme.typography.labelLarge,
		)
		MapisodeTextField(
			modifier = modifier.fillMaxWidth(),
			value = value,
			placeholder = stringResource(placeholderRes),
			onValueChange = onValueChange,
			readOnly = readOnly,
			trailingIcon = {
				trailingIcon?.let {
					MapisodeIconButton(
						onClick = onTrailingIconClick ?: {},
					) { it() }
				}
			},
			isError = isError,
			onSubmitInput = onSubmitInput,
		)
	}
}
