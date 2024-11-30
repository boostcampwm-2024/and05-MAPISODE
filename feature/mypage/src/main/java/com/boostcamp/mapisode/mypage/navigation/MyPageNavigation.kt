package com.boostcamp.mapisode.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.boostcamp.mapisode.mypage.screen.MypageRoute
import com.boostcamp.mapisode.mypage.screen.ProfileEditRoute
import com.boostcamp.mapisode.navigation.MainRoute
import com.boostcamp.mapisode.navigation.MypageRoute

fun NavController.navigateMyPage(
	navOptions: NavOptions? = null,
) {
	navigate(MainRoute.Mypage, navOptions)
}

fun NavController.navigateToProfileEdit(
	navOptions: NavOptions? = null,
) {
	navigate(MypageRoute.ProfileEdit, navOptions)
}

fun NavGraphBuilder.addMyPageNavGraph(
	onBackClick: () -> Unit,
	onProfileEditClick: () -> Unit,
) {
	composable<MainRoute.Mypage> {
		MypageRoute(
			onProfileEditClick = onProfileEditClick,
		)
	}

	composable<MypageRoute.ProfileEdit> {
		ProfileEditRoute(
			onBackClick = onBackClick,
		)
	}
}
