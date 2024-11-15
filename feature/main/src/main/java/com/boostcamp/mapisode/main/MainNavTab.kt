package com.boostcamp.mapisode.main

import androidx.compose.runtime.Composable
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.navigation.MainRoute

internal enum class MainNavTab(
	val iconResId: Int,
	internal val contentDescription: String,
	val route: MainRoute,
) {
	HOME(
		iconResId = R.drawable.ic_house_24,
		contentDescription = "홈",
		route = MainRoute.Home,
	),
	EPISODE(
		iconResId = R.drawable.ic_edit_note_24,
		contentDescription = "새 에피소드",
		route = MainRoute.Episode,
	),
	GROUP(
		iconResId = R.drawable.ic_groups_2_24,
		contentDescription = "그룹",
		route = MainRoute.Group,
	),
	MYPAGE(
		iconResId = R.drawable.ic_account_circle_24,
		contentDescription = "마이페이지",
		route = MainRoute.Mypage,
	), ;

	companion object {
		@Composable
		fun find(predicate: @Composable (MainRoute) -> Boolean): MainNavTab? =
			entries.find { predicate(it.route) }

		@Composable
		fun contains(predicate: @Composable (MainRoute) -> Boolean): Boolean =
			entries.map { it.route }.any { predicate(it) }
	}
}
