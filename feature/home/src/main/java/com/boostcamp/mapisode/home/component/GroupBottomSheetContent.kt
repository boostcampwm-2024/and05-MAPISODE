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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import coil3.test.FakeImage
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.AppTypography
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.home.common.HomeConstant.tempGroupList
import com.boostcamp.mapisode.model.GroupModel
import kotlinx.collections.immutable.PersistentList
import com.boostcamp.mapisode.home.R as Home

@Composable
fun GroupBottomSheetContent(
	groupList: PersistentList<GroupModel>,
	modifier: Modifier = Modifier,
	onDismiss: () -> Unit = {},
	onGroupClick: (String) -> Unit = {},
) {
	Column(
		modifier = modifier.fillMaxWidth(),
	) {
		Box(
			modifier = Modifier.fillMaxWidth(),
			contentAlignment = Alignment.Center,
		) {
			MapisodeText(
				text = stringResource(Home.string.group_bottomsheet_title),
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
			items(groupList) { group ->
				GroupCard(
					groupId = group.id,
					groupImage = group.imageUrl,
					groupName = group.name,
					onClick = onGroupClick,
				)
			}
		}
	}
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true)
@Composable
fun GroupBottomSheetContentPreview() {
	val previewHandler = AsyncImagePreviewHandler {
		FakeImage(color = 0xFFE0E0E0.toInt())
	}

	CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
		MapisodeTheme {
			GroupBottomSheetContent(
				groupList = tempGroupList,
			)
		}
	}
}
