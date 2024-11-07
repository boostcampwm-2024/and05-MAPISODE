package com.boostcamp.mapisode.mypage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
internal fun MyPageRoute() {
	MyPageScreen()
}

@Composable
private fun MyPageScreen(
) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		Text(text = "MyPageScreen", style = MaterialTheme.typography.displayMedium)
	}
}
