package com.boostcamp.mapisode.mygroup

import androidx.compose.runtime.Composable
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar

@Composable
fun GroupJoinScreen(
	onBackClick: () -> Unit,
) {
	MapisodeScaffold(
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
	) {}
}
