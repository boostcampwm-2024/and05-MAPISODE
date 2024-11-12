package com.boostcamp.mapisode.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.BottomBar
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.LocalMapisodeTypography
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun TestScreen() {
	MapisodeScaffold(
		topBar = {
			TopAppBar(
				title = "커스텀 테마 앱 바",
				modifier = Modifier.testTag("TopAppBar"),
				navigationIcon = {
					MapisodeIcon(
						id = R.drawable.ic_arrow_back_24,
					)
				},
				actions = {
					Row {
						MapisodeIcon(id = R.drawable.ic_groups_2_24)
						MapisodeIcon(id = R.drawable.ic_house_24)
					}
				},
			)
		},
		bottomBar = {
			BottomBar(
				modifier = Modifier.testTag("BottomAppBar"),
			) {
				MapisodeText(text = "Custom BottomBar Content", modifier = Modifier.padding(16.dp))
			}
		},
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(16.dp),
		) {
			MapisodeText(text = "디플 라지", style = LocalMapisodeTypography.current.displayLarge)
			MapisodeText(text = "디플 미디엄", style = LocalMapisodeTypography.current.displayMedium)
			MapisodeText(text = "디플 스몰", style = LocalMapisodeTypography.current.displaySmall)

			MapisodeText(text = "헤드 라지", style = LocalMapisodeTypography.current.headlineLarge)
			MapisodeText(text = "헤드 미디엄", style = LocalMapisodeTypography.current.headlineMedium)
			MapisodeText(text = "헤드 스몰", style = LocalMapisodeTypography.current.headlineSmall)

			MapisodeText(text = "바디 라지", style = LocalMapisodeTypography.current.bodyLarge)
			MapisodeText(text = "바디 미디엄", style = LocalMapisodeTypography.current.bodyMedium)
			MapisodeText(text = "바디 스몰", style = LocalMapisodeTypography.current.bodySmall)
		}
	}
}

@Preview(apiLevel = 34)
@Composable
fun TestScreenPreview() {
	MapisodeTheme {
		TestScreen()
	}
}
