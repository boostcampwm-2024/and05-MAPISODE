package com.boostcamp.mapisode.mygroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeOutlinedButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar

@Composable
internal fun GroupRoute() {
	GroupScreen()
}

@Composable
private fun GroupScreen() {
	MapisodeScaffold(
		modifier = Modifier
			.fillMaxSize(),
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
                .padding(it),
			verticalArrangement = Arrangement.spacedBy(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			MapisodeFilledButton(
				onClick = { },
				text = "활성화 버튼",
			)

			MapisodeFilledButton(
				onClick = { },
				text = "비활성화 버튼",
				enabled = false,
			)

			MapisodeOutlinedButton(
				onClick = { },
				text = "아웃라인 버튼",
			)
		}
	}
}
