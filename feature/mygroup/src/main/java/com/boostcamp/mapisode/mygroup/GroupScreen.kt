package com.boostcamp.mapisode.mygroup

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.MapisodeTextField
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeOutlinedButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
internal fun GroupRoute() {
	GroupScreen()
}

@Composable
private fun GroupScreen() {
	val focusManager = LocalFocusManager.current

	MapisodeScaffold(
		modifier = Modifier
			.fillMaxSize()
			.pointerInput(Unit) {
				detectTapGestures(
					onPress = {
						focusManager.clearFocus()
					},
				)
			},
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = "나의 그룹",
			)
		},
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
				.padding(horizontal = 46.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			var inputText by remember { mutableStateOf("") }
			var submittedText by remember { mutableStateOf("") }

			MapisodeFilledButton(
				onClick = {
					focusManager.clearFocus()
				},
				text = "활성화 버튼",
				showRipple = true,
			)

			MapisodeFilledButton(
				onClick = { },
				text = "비활성화 버튼",
				enabled = false,
			)

			MapisodeOutlinedButton(
				onClick = { submittedText = "아웃라인 버튼 클릭" },
				text = "아웃라인 버튼",
				showRipple = true,
			)

			MapisodeTextField(
				value = inputText,
				onValueChange = { text -> inputText = text },
				placeholder = "텍스트 필드",
				onSubmitInput = { text ->
					submittedText = text
				},
			)

			MapisodeText(
				text = submittedText,
				style = MapisodeTheme.typography.bodyLarge,
			)

			MapisodeTextField(
				value = inputText,
				onValueChange = { text -> inputText = text },
				placeholder = "위치를 입력하세요",
				onSubmitInput = { text ->
					submittedText = text
				},
				trailingIcon = {
					MapisodeIcon(
						id = R.drawable.ic_location,
					)
				},
			)
		}
	}
}
