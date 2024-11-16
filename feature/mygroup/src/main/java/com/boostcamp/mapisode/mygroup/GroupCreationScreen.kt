package com.boostcamp.mapisode.mygroup

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
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton

@Composable
fun GroupCreationScreen() {
	var isNavigationIconEnabled by remember { mutableStateOf(true) }

	MapisodeScaffold(
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = "그룹 생성",
				navigationIcon = {
					MapisodeIconButton(
						onClick = { },
						enabled = isNavigationIconEnabled,
					) {
						MapisodeIcon(
							id = R.drawable.ic_arrow_back_ios,
						)
					}
				},
			)
		},
	) {
		Column(
			modifier = Modifier.fillMaxSize().padding(it),
			verticalArrangement = Arrangement.spacedBy(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			MapisodeFilledButton(
				onClick = { isNavigationIconEnabled = !isNavigationIconEnabled },
				text = "네비게이션 아이콘 활성/비활성화",
				showRipple = true,
			)
		}
	}
}
