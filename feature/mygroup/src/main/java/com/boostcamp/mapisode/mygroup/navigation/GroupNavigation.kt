package com.boostcamp.mapisode.mygroup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.boostcamp.mapisode.mygroup.screen.GroupCreationScreen
import com.boostcamp.mapisode.mygroup.screen.GroupDetailScreen
import com.boostcamp.mapisode.mygroup.screen.GroupEditScreen
import com.boostcamp.mapisode.mygroup.screen.GroupJoinScreen
import com.boostcamp.mapisode.mygroup.screen.MainGroupRoute
import com.boostcamp.mapisode.navigation.GroupRoute
import com.boostcamp.mapisode.navigation.MainRoute

fun NavController.navigateGroupJoin(
	navOptions: NavOptions? = null,
) {
	navigate(GroupRoute.Join, navOptions)
}

fun NavController.navigateGroupDetail(
	groupId: String,
	navOptions: NavOptions? = null,
) {
	navigate(GroupRoute.Detail(groupId), navOptions)
}

fun NavController.navigateGroupCreation(
	navOptions: NavOptions? = null,
) {
	navigate(GroupRoute.Creation, navOptions)
}

fun NavController.navigateGroupEdit(
	groupId: String,
	navOptions: NavOptions? = null,
) {
	navigate(GroupRoute.Edit(groupId), navOptions)
}

fun NavGraphBuilder.addGroupNavGraph(
	onBackClick: () -> Unit,
	onGroupJoinClick: () -> Unit,
	onGroupDetailClick: (String) -> Unit,
	onGroupCreationClick: () -> Unit,
	onGroupEditClick: (String) -> Unit,
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

	composable<GroupRoute.Detail> {backStackEntry ->
		val detail : GroupRoute.Detail = backStackEntry.toRoute()

		GroupDetailScreen(
			detail = detail,
			onBackClick = onBackClick,
			onEditClick = onGroupEditClick,
		)
	}

	composable<GroupRoute.Creation> {
		GroupCreationScreen(
			onBackClick = onBackClick,
		)
	}

	composable<GroupRoute.Edit> {backStackEntry ->
		val edit : GroupRoute.Edit = backStackEntry.toRoute()

		GroupEditScreen(
			edit = edit,
			onBackClick = onBackClick,
		)
	}
}
