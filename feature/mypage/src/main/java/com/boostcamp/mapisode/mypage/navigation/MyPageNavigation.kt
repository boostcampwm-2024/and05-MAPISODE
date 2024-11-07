package com.boostcamp.mapisode.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.boostcamp.mapisode.mypage.MyPageRoute
import com.boostcamp.mapisode.navigation.MainRoute

fun NavController.navigateMyPage(
	navOptions: NavOptions? = null,
) {
	navigate(MainRoute.Mypage, navOptions)
}

fun NavGraphBuilder.addMyPageNavGraph() {
	composable<MainRoute.Mypage> {
		MyPageRoute()
	}
}
