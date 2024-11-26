package com.boostcamp.mapisode.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.boostcamp.mapisode.login.AuthRoute
import com.boostcamp.mapisode.navigation.AuthRoute

fun NavGraphBuilder.addAuthNavGraph(
	navigateToMain: () -> Unit,
) {
	composable<AuthRoute> {
		AuthRoute(navigateToMain)
	}
}
