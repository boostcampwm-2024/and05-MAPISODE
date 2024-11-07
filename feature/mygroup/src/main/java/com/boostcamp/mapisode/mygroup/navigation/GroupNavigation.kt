package com.boostcamp.mapisode.mygroup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.boostcamp.mapisode.mygroup.GroupRoute
import com.boostcamp.mapisode.navigation.MainRoute

fun NavController.navigateGroup(
	navOptions: NavOptions? = null,
) {
	navigate(MainRoute.Group, navOptions)
}

fun NavGraphBuilder.addGroupNavGraph() {
	composable<MainRoute.Group> {
		GroupRoute()
	}
}
