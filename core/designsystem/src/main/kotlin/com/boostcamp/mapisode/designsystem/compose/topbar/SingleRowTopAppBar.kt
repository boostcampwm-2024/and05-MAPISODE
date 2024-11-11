package com.boostcamp.mapisode.designsystem.compose.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.compose.Surface
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
internal fun SingleRowTopAppBar(
	modifier: Modifier = Modifier,
	backgroundColor: Color = MapisodeTheme.colorScheme.background,
	contentColor: Color = MapisodeTheme.colorScheme.foreground,
	contentPadding: PaddingValues,
	content: @Composable RowScope.() -> Unit,
) {
	AppBar(
		backgroundColor,
		contentColor,
		contentPadding,
		RectangleShape,
		modifier = modifier,
		content = content,
	)
}

@Composable
private fun AppBar(
	backgroundColor: Color,
	contentColor: Color,
	contentPadding: PaddingValues,
	shape: Shape,
	modifier: Modifier = Modifier,
	content: @Composable RowScope.() -> Unit,
) {
	Surface(
		color = backgroundColor,
		contentColor = contentColor,
		shape = shape,
		modifier = modifier,
	) {
		Row(
            Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .height(56.dp),
			horizontalArrangement = Arrangement.Start,
			verticalAlignment = Alignment.CenterVertically,
			content = content,
		)
	}
}
