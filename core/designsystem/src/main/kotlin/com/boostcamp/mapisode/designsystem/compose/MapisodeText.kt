package com.boostcamp.mapisode.designsystem.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.boostcamp.mapisode.designsystem.theme.AppTypography
import com.boostcamp.mapisode.designsystem.theme.LocalMapisodeContentAlpha
import com.boostcamp.mapisode.designsystem.theme.LocalMapisodeContentColor
import com.boostcamp.mapisode.designsystem.theme.MapisodeTextStyle
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun MapisodeText(
	text: String,
	modifier: Modifier = Modifier,
	color: Color = MapisodeTheme.colorScheme.textContent,
	onTextLayout: (TextLayoutResult) -> Unit = {},
	style: MapisodeTextStyle = LocalTextStyle.current,
	overflow: TextOverflow = TextOverflow.Clip,
	maxLines: Int = Int.MAX_VALUE,
	minLines: Int = 1,
) {
	val textColor = color.takeOrElse {
		style.color.takeOrElse {
			LocalMapisodeContentColor.current.copy(alpha = LocalMapisodeContentAlpha.current)
		}
	}

	val mergedStyle = style.copy(color = textColor).toTextStyle()

	BasicText(
		text = text,
		modifier = modifier,
		style = mergedStyle,
		onTextLayout = onTextLayout,
		overflow = overflow,
		maxLines = maxLines,
		minLines = minLines,
	)
}

val LocalTextStyle = compositionLocalOf { MapisodeTextStyle.Default }

@Composable
fun ProvideTextStyle(value: MapisodeTextStyle, content: @Composable () -> Unit) {
	val mergedStyle = LocalTextStyle.current.merge(value)
	CompositionLocalProvider(LocalTextStyle provides mergedStyle, content = content)
}

@Preview(showBackground = true)
@Composable
fun MapisodeTextPreview() {
	Column {
		// Display 스타일
		MapisodeText(
			text = "디스플레이 라지",
			style = AppTypography.displayLarge,
		)
		MapisodeText(
			text = "디스플레이 미디엄",
			style = AppTypography.displayMedium,
		)
		MapisodeText(
			text = "디스플레이 스몰",
			style = AppTypography.displaySmall,
		)

		// Headline 스타일
		MapisodeText(
			text = "헤드라인 라지",
			style = AppTypography.headlineLarge,
		)
		MapisodeText(
			text = "헤드라인 미디엄",
			style = AppTypography.headlineMedium,
		)
		MapisodeText(
			text = "헤드라인 스몰",
			style = AppTypography.headlineSmall,
		)

		// Title 스타일
		MapisodeText(
			text = "타이틀 라지",
			style = AppTypography.titleLarge,
		)
		MapisodeText(
			text = "타이틀 미디엄",
			style = AppTypography.titleMedium,
		)
		MapisodeText(
			text = "타이틀 스몰",
			style = AppTypography.titleSmall,
		)

		// Body 스타일
		MapisodeText(
			text = "본문 라지",
			style = AppTypography.bodyLarge,
		)
		MapisodeText(
			text = "본문 미디엄",
			style = AppTypography.bodyMedium,
		)
		MapisodeText(
			text = "본문 스몰",
			style = AppTypography.bodySmall,
		)

		// Label 스타일
		MapisodeText(
			text = "라벨 라지",
			style = AppTypography.labelLarge,
		)
		MapisodeText(
			text = "라벨 미디엄",
			style = AppTypography.labelMedium,
		)
		MapisodeText(
			text = "라벨 스몰",
			style = AppTypography.labelSmall,
		)
	}
}
