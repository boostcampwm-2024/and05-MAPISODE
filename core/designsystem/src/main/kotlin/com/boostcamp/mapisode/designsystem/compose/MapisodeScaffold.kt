package com.boostcamp.mapisode.designsystem.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import com.boostcamp.mapisode.designsystem.compose.toast.ToastHost
import com.boostcamp.mapisode.designsystem.compose.toast.ToastHostState
import com.boostcamp.mapisode.designsystem.compose.toast.rememberToastHostState
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

private enum class ScaffoldLayoutContent { TopBar, MainContent, Snackbar, BottomBar }

@Composable
fun MapisodeScaffold(
	modifier: Modifier = Modifier,
	isStatusBarPaddingExist: Boolean = false,
	isNavigationBarPaddingExist: Boolean = false,
	toastHostState: ToastHostState = rememberToastHostState(),
	topBar: @Composable () -> Unit = {},
	bottomBar: @Composable () -> Unit = {},
	toastHost: @Composable (ToastHostState) -> Unit = { ToastHost(it) },
	backgroundColor: Color = MapisodeTheme.colorScheme.scaffoldBackground,
	contentColor: Color = MapisodeTheme.colorScheme.textContent,
	content: @Composable (PaddingValues) -> Unit,
) {
	Surface(
		modifier = modifier,
		color = backgroundColor,
		contentColor = contentColor,
	) {
		ScaffoldLayout(
			isStatusBarPaddingExist = isStatusBarPaddingExist,
			isNavigationBarPaddingExist = isNavigationBarPaddingExist,
			topBar = topBar,
			bottomBar = bottomBar,
			toast = { toastHost(toastHostState) },
			content = content,
		)
	}
}

@Composable
private fun ScaffoldLayout(
	isStatusBarPaddingExist: Boolean,
	isNavigationBarPaddingExist: Boolean = false,
	topBar: @Composable () -> Unit,
	bottomBar: @Composable () -> Unit,
	toast: @Composable () -> Unit,
	content: @Composable (PaddingValues) -> Unit,
) {
	val topPadding = if (isStatusBarPaddingExist) {
		WindowInsets.statusBars.asPaddingValues()
			.calculateTopPadding()
	} else {
		0.dp
	}
	val bottomPadding = if (isNavigationBarPaddingExist) {
		WindowInsets.navigationBars.asPaddingValues()
			.calculateBottomPadding()
	} else {
		0.dp
	}
	val systemBar = WindowInsets(top = topPadding, bottom = bottomPadding)

	SubcomposeLayout { constraints ->
		val layoutWidth = constraints.maxWidth
		val layoutHeight = constraints.maxHeight

		val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

		val topBarPlaceables = subcompose(ScaffoldLayoutContent.TopBar, topBar).map {
			it.measure(looseConstraints)
		}
		val topBarHeight = topBarPlaceables.fastMaxBy { it.height }?.height ?: 0

		val toastPlaceables =
			subcompose(ScaffoldLayoutContent.Snackbar, toast).fastMap {
				val bottomInset = systemBar.getBottom(this@SubcomposeLayout)
				it.measure(looseConstraints.offset(vertical = -bottomInset))
			}
		val toastHeight = toastPlaceables.fastMaxBy { it.height }?.height ?: 0
		val toastWidth = toastPlaceables.fastMaxBy { it.width }?.width ?: 0

		val bottomBarPlaceables =
			subcompose(ScaffoldLayoutContent.BottomBar) { bottomBar() }
				.fastMap { it.measure(looseConstraints) }

		val bottomBarHeight = bottomBarPlaceables.fastMaxBy { it.height }?.height

		val toastOffsetFromBottom =
			if (toastHeight != 0) {
				toastHeight +
					(bottomBarHeight ?: systemBar.getBottom(this@SubcomposeLayout))
			} else {
				0
			}

		val bodyContentPlaceables =
			subcompose(ScaffoldLayoutContent.MainContent) {
				val insets = systemBar.asPaddingValues(this@SubcomposeLayout)
				val innerPadding =
					PaddingValues(
						top = if (topBarPlaceables.isEmpty()) {
							insets.calculateTopPadding()
						} else {
							topBarHeight.toDp()
						},
						bottom =
						if (bottomBarPlaceables.isEmpty() || bottomBarHeight == null) {
							insets.calculateBottomPadding()
						} else {
							bottomBarHeight.toDp()
						},
					)
				content(innerPadding)
			}
				.fastMap {
					val statusBarHeight = systemBar.getTop(this@SubcomposeLayout)
					val adjustedConstraints = looseConstraints.copy(
						maxHeight = looseConstraints.maxHeight - statusBarHeight,
					)
					it.measure(adjustedConstraints)
				}

		layout(layoutWidth, layoutHeight) {
			val topBarOffset = systemBar.getTop(this@SubcomposeLayout)

			bodyContentPlaceables.fastForEach { it.place(0, topBarOffset) }
			topBarPlaceables.fastForEach { it.place(0, topBarOffset) }
			toastPlaceables.fastForEach {
				it.place(
					(layoutWidth - toastWidth) / 2 +
						systemBar.getLeft(this@SubcomposeLayout, layoutDirection),
					layoutHeight - toastOffsetFromBottom,
				)
			}
			bottomBarPlaceables.fastForEach { it.place(0, layoutHeight - (bottomBarHeight ?: 0)) }
		}
	}
}
