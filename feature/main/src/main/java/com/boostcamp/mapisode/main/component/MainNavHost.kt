package com.boostcamp.mapisode.main.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.boostcamp.mapisode.episode.navigation.addEpisodeNavGraph
import com.boostcamp.mapisode.home.navigation.addHomeNavGraph
import com.boostcamp.mapisode.login.navigation.addAuthNavGraph
import com.boostcamp.mapisode.main.MainNavTab
import com.boostcamp.mapisode.main.MainNavigator
import com.boostcamp.mapisode.mygroup.navigation.addGroupNavGraph
import com.boostcamp.mapisode.mypage.navigation.addMyPageNavGraph

@Composable
internal fun MainNavHost(
	modifier: Modifier = Modifier,
	navigator: MainNavigator,
) {
	Box(
		modifier = modifier.fillMaxSize(),
	) {
		NavHost(
			navController = navigator.navController,
			startDestination = navigator.startDestination,
		) {
			addHomeNavGraph(
				navController = navigator.navController,
				onTextMarkerClick = { latLng ->
					navigator.navigate(MainNavTab.EPISODE, latLng)
				},
				onEpisodeEditClick = navigator::navigateToEpisodeEdit,
				onEpisodeClick = navigator::navigateToEpisodeDetail,
				onListFabClick = navigator::navigateToEpisodeList,
				onStoryClick = navigator::navigateToStoryViewer,
				onBackClick = navigator::popBackStackIfNotHome,
			)
			addAuthNavGraph(
				navigateToMain = navigator::navigateToMain,
			)
			addEpisodeNavGraph(
				getBackStackEntry = navigator::getEpisodeBackStackEntry,
				onPopBackToMain = navigator::popBackEpisodeToMain,
				onPopBack = navigator::popBackStackIfNotHome,
				onNavigateToInfo = navigator::navigateWriteInfo,
				onNavigateToContent = navigator::navigateWriteContent,
				onClickPickLocation = navigator::navigatePickLocation,
			)
			addGroupNavGraph(
				onBackClick = navigator::popBackStackIfNotHome,
				onGroupJoinClick = navigator::navigateGroupJoin,
				onGroupDetailClick = { groupId: String ->
					navigator.navigateGroupDetail(groupId)
				},
				onGroupCreationClick = navigator::navigateGroupCreation,
				onGroupEditClick = { groupId: String ->
					navigator.navigateGroupEdit(groupId)
				},
				onEpisodeClick = navigator::navigateToEpisodeDetail,
			)
			addMyPageNavGraph(
				onBackClick = navigator::popBackStackIfNotHome,
				onProfileEditClick = navigator::navigateToProfileEdit,
				onNavgiatetoLogin = navigator::navigateToLogin,
			)
		}
	}
}
