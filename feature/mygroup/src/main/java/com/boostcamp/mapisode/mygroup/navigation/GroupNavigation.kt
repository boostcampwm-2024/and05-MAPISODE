package com.boostcamp.mapisode.mygroup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.boostcamp.mapisode.mygroup.GroupCreationScreen
import com.boostcamp.mapisode.mygroup.GroupDetailScreen
import com.boostcamp.mapisode.mygroup.GroupEditScreen
import com.boostcamp.mapisode.mygroup.GroupJoinScreen
import com.boostcamp.mapisode.mygroup.MainGroupRoute
import com.boostcamp.mapisode.navigation.GroupRoute
import com.boostcamp.mapisode.navigation.MainRoute

fun NavController.navigateGroupJoin(
	navOptions: NavOptions? = null,
) {
	navigate(GroupRoute.Join, navOptions)
}

fun NavController.navigateGroupDetail(
	navOptions: NavOptions? = null,
) {
	navigate(GroupRoute.Detail, navOptions)
}

fun NavController.navigateGroupCreation(
	navOptions: NavOptions? = null,
) {
	navigate(GroupRoute.Creation, navOptions)
}

fun NavController.navigateGroupEdit(
	navOptions: NavOptions? = null,
) {
	navigate(GroupRoute.Edit, navOptions)
}

fun NavGraphBuilder.addGroupNavGraph(
	onBackClick: () -> Unit,
	onGroupJoinClick: () -> Unit,
	onGroupDetailClick: () -> Unit,
	onGroupCreationClick: () -> Unit,
	onGroupEditClick: () -> Unit,
) {
	composable<MainRoute.Group> {
		MainGroupRoute(
			onGroupJoinClick = onGroupJoinClick,
			onGroupDetailClick = onGroupDetailClick,
			onGroupCreationClick = onGroupCreationClick,
		)
	}

	composable<GroupRoute.Join> {
		GroupJoinScreen(
			onBackClick = onBackClick,
		)
	}

	composable<GroupRoute.Detail> {
		GroupDetailScreen(
			onBackClick = onBackClick,
			onEditClick = onGroupEditClick,
		)
	}

	composable<GroupRoute.Creation> {
		GroupCreationScreen(
			onBackClick = onBackClick,
		)
	}

	composable<GroupRoute.Edit> {
		GroupEditScreen(
			onBackClick = onBackClick,
		)
	}
}
