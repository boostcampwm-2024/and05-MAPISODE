package com.boostcamp.mapisode.designsystem.compose.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun TopAppBar(
	modifier: Modifier = Modifier,
	title: String = "",
	navigationIcon: @Composable () -> Unit = {},
	actions: @Composable RowScope.() -> Unit = {},
) {
	SingleRowTopAppBar(
		modifier = modifier
			.fillMaxWidth()
			.height(56.dp),
		contentPadding = PaddingValues(horizontal = 4.dp)
	) {
		Box(
			modifier = Modifier.fillMaxWidth()
				.padding(horizontal = 8.dp)
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically, modifier = Modifier
					.fillMaxHeight()
					.align(Alignment.TopStart)
					.padding(0.dp)
			) {
				navigationIcon()
			}

			Row(
				modifier = Modifier
					.fillMaxHeight()
					.padding(0.dp)
					.align(Alignment.TopCenter),
				verticalAlignment = Alignment.CenterVertically,
			) {
				MapisodeText(
					text = title,
					style = MapisodeTheme.typography.titleLarge,
					color = MapisodeTheme.colorScheme.foreground
				)
			}

			Row(
				modifier = Modifier
					.fillMaxHeight()
					.padding(0.dp)
					.align(Alignment.TopEnd),
				verticalAlignment = Alignment.CenterVertically
			) {
				actions()
			}
		}
	}
}
