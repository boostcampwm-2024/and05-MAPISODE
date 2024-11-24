package com.boostcamp.mapisode.mygroup.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.MapisodeTextField
import com.boostcamp.mapisode.designsystem.compose.TextAlignment
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.mygroup.R as S

@Composable
fun GroupJoinScreen(
	onBackClick: () -> Unit,
) {
	val focusManager = LocalFocusManager.current
	var codeText by remember { mutableStateOf("") }

	MapisodeScaffold(
		modifier = Modifier.pointerInput(Unit) {
			detectTapGestures(
				onPress = {
					focusManager.clearFocus()
				},
			)
		},
		isNavigationBarPaddingExist = true,
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = "그룹 참여",
				navigationIcon = {
					MapisodeIconButton(
						onClick = onBackClick,
					) {
						MapisodeIcon(
							id = R.drawable.ic_arrow_back_ios,
							contentDescription = "Back",
						)
					}
				},
			)
		},
	) {
		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
				.padding(horizontal = 20.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			item {
				Column(
					modifier = Modifier.padding(vertical = 8.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
				) {
					MapisodeText(
						text = stringResource(S.string.group_join_sub_title),
						style = MapisodeTheme.typography.titleLarge,
					)
					Spacer(modifier = Modifier.padding(8.dp))
					MapisodeText(
						modifier = Modifier.fillParentMaxWidth(),
						textAlignment = TextAlignment.Center,
						text = stringResource(S.string.group_join_direction),
					)
				}
			}
			item {
				Column(
					modifier = Modifier.padding(vertical = 20.dp),
				) {
					MapisodeText(
						modifier = Modifier.fillParentMaxWidth(),
						text = stringResource(S.string.group_join_textinput_label),
						style = MapisodeTheme.typography.labelLarge,
					)
					Spacer(modifier = Modifier.padding(4.dp))
					MapisodeTextField(
						modifier = Modifier.fillParentMaxWidth(),
						value = codeText,
						onValueChange = { text -> codeText = text },
						placeholder = stringResource(S.string.group_join_textfield_hint),
					)
				}
			}
			item {
				MapisodeFilledButton(
					modifier = Modifier
						.fillParentMaxWidth()
						.height(40.dp),
					onClick = {
						// TODO : 서버에 코드 요청
					},
					text = stringResource(S.string.group_join_btn_direction),
					enabled = codeText.isNotBlank(),
					showRipple = true,
				)
				Spacer(modifier = Modifier.padding(10.dp))
				Box(
					Modifier
						.fillMaxWidth()
						.height(1.dp)
						.background(color = MapisodeTheme.colorScheme.textFieldContent)
				)
			}
		}

	}
}
