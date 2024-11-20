package com.boostcamp.mapisode.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.AppTypography
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.home.common.HomeConstant.tempGroupList
import com.boostcamp.mapisode.model.GroupItem
import kotlinx.collections.immutable.PersistentList

@Composable
fun GroupBottomSheetContent(
	myProfileImage: String,
	groupList: PersistentList<GroupItem>,
	modifier: Modifier = Modifier,
	onDismiss: () -> Unit = {},
) {
	Column(
		modifier = modifier.fillMaxWidth(),
	) {
		Box(
			modifier = Modifier.fillMaxWidth(),
			contentAlignment = Alignment.Center,
		) {
			MapisodeText(
				text = stringResource(com.boostcamp.mapisode.home.R.string.group_bottomsheet_title),
				color = MapisodeTheme.colorScheme.textContent,
				style = AppTypography.titleLarge.copy(fontWeight = FontWeight.Normal),
			)

			MapisodeIconButton(
				onClick = onDismiss,
				modifier = Modifier
					.align(Alignment.CenterEnd)
					.padding(end = 24.dp),
			) {
				MapisodeIcon(id = R.drawable.ic_clear)
			}
		}

		LazyVerticalGrid(
			columns = GridCells.Fixed(2),
			modifier = Modifier.fillMaxWidth(),
			contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
			verticalArrangement = Arrangement.spacedBy(20.dp),
			horizontalArrangement = Arrangement.Center,
		) {
			item {
				GroupCard(
					groupImage = myProfileImage,
					groupName = stringResource(com.boostcamp.mapisode.home.R.string.group_my_episode_name),
				)
			}

			items(groupList) { group ->
				GroupCard(
					groupImage = group.imageUrl,
					groupName = group.name,
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun GroupBottomSheetContentPreview() {
	MapisodeTheme {
		GroupBottomSheetContent(
			myProfileImage = "",
			groupList = tempGroupList,
		)
	}
}