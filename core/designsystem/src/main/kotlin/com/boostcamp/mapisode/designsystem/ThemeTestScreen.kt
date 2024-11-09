package com.boostcamp.mapisode.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.theme.LocalMapisodeTypography
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun TestScreen() {
	MapisodeTheme {
		Scaffold(
			topBar = {
				TopAppBar(
					title = { Text(text = "커스텀 테마 앱 바") },
					modifier = Modifier.testTag("TopAppBar"),
				)
			},
			bottomBar = {
				BottomAppBar(
					modifier = Modifier.testTag("BottomAppBar"),
				) {
					Text(text = "Custom BottomBar Content", modifier = Modifier.padding(16.dp))
				}
			},
		) { paddingValues ->
			Column(
				modifier = Modifier
					.fillMaxSize()
					.padding(paddingValues)
					.padding(16.dp),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.spacedBy(16.dp),
			) {
				Button(
					onClick = { },
					modifier = Modifier.testTag("Button1"),
					colors = ButtonDefaults.buttonColors(
						MapisodeTheme.colorScheme.accentSelected,
					),
				) {
					Text("Primary Button")
				}

				OutlinedButton(
					onClick = { },
					modifier = Modifier.testTag("Button2"),
				) {
					Text("Secondary Button")
				}

				Text(text = "디플 라지", style = LocalMapisodeTypography.current.displayLarge)
				Text(text = "디플 미디엄", style = LocalMapisodeTypography.current.displayMedium)
				Text(text = "디플 스몰", style = LocalMapisodeTypography.current.displaySmall)

				Text(text = "헤드 라지", style = LocalMapisodeTypography.current.headlineLarge)
				Text(text = "헤드 미디엄", style = LocalMapisodeTypography.current.headlineMedium)
				Text(text = "헤드 스몰", style = LocalMapisodeTypography.current.headlineSmall)

				Text(text = "바디 라지", style = LocalMapisodeTypography.current.bodyLarge)
				Text(text = "바디 미디엄", style = LocalMapisodeTypography.current.bodyMedium)
				Text(text = "바디 스몰", style = LocalMapisodeTypography.current.bodySmall)
			}
		}
	}
}

@Preview(apiLevel = 34)
@Composable
fun TestScreenPreview() {
	TestScreen()
}
