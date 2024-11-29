package com.boostcamp.mapisode.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.boostcamp.mapisode.episode.navigation.navigatePickLocation
import com.boostcamp.mapisode.episode.navigation.navigateWriteContent
import com.boostcamp.mapisode.episode.navigation.navigateWriteInfo
import com.boostcamp.mapisode.home.navigation.navigateEpisodeDetail
import com.boostcamp.mapisode.mygroup.navigation.navigateGroupCreation
import com.boostcamp.mapisode.mygroup.navigation.navigateGroupDetail
import com.boostcamp.mapisode.mygroup.navigation.navigateGroupEdit
import com.boostcamp.mapisode.mygroup.navigation.navigateGroupJoin
import com.boostcamp.mapisode.navigation.MainRoute
import com.boostcamp.mapisode.navigation.Route

internal class MainNavigator(
	val navController: NavHostController,
) {
	private val currentDestination: NavDestination?
		@Composable get() = navController
			.currentBackStackEntryAsState().value?.destination

	val startDestination = Route.Auth

	val currentTab: MainNavTab?
		@Composable get() = MainNavTab.find { tab ->
			currentDestination?.hasRoute(tab::class) == true
		}

	fun navigate(tab: MainNavTab) {
		val navOptions = navOptions {
			popUpTo(navController.graph.findStartDestination().id) {
				saveState = true
			}
			launchSingleTop = true
			restoreState = true
		}

		when (tab) {
			MainNavTab.HOME -> navController.navigate(MainNavTab.HOME.route, navOptions)
			MainNavTab.EPISODE -> navController.navigate(MainNavTab.EPISODE.route, navOptions)
			MainNavTab.GROUP -> navController.navigate(MainNavTab.GROUP.route, navOptions)
			MainNavTab.MYPAGE -> navController.navigate(MainNavTab.MYPAGE.route, navOptions)
		}
	}

	var endSplash by mutableStateOf(false)

	fun navigateToMain() {
		navController.navigate(MainRoute.Home)
	}

	fun endSplash(end: Boolean) {
		endSplash = end
	}

	fun navigateToEpisodeDetail(episodeId: String) {
		navController.navigateEpisodeDetail(episodeId)
	}

	fun getEpisodeBackStackEntry(): NavBackStackEntry =
		navController.getBackStackEntry(startDestination)

	fun popBackEpisodeToMain() {
		navController.popBackStack(MainRoute.Episode, inclusive = true)
	}

	fun navigateWriteInfo() {
		navController.navigateWriteInfo()
	}

	fun navigatePickLocation() {
		navController.navigatePickLocation()
	}

	fun navigateWriteContent() {
		navController.navigateWriteContent()
	}

	fun navigateGroupJoin() {
		navController.navigateGroupJoin()
	}

	fun navigateGroupDetail(groupId: String) {
		navController.navigateGroupDetail(groupId)
	}

	fun navigateGroupCreation() {
		navController.navigateGroupCreation()
	}

	fun navigateGroupEdit(groupId: String) {
		navController.navigateGroupEdit(groupId)
	}

	private fun popBackStack() {
		navController.popBackStack()
	}

	fun popBackStackIfNotHome() {
		if (!isSameCurrentDestination<MainRoute.Home>()) {
			popBackStack()
		}
	}

	private inline fun <reified T : MainRoute> isSameCurrentDestination(): Boolean =
		navController.currentDestination?.hasRoute<T>() == true

	@Composable
	fun shouldShowBottomBar() = MainNavTab.contains {
		currentDestination?.hasRoute(it::class) == true
	}
}

@Composable
internal fun rememberMainNavigator(
	navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
	MainNavigator(navController)
}
