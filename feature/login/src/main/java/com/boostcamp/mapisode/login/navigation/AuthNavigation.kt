package com.boostcamp.mapisode.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.mapisode.login.AuthRoute
import com.boostcamp.mapisode.navigation.Route

fun NavGraphBuilder.addAuthNavGraph(
	navigateToMain: () -> Unit,
	endSplash: (Boolean) -> Unit,
) {
	composable<Route.Auth> {
		AuthRoute(navigateToMain, endSplash)
	}
}
