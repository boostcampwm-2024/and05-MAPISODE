package com.boostcamp.mapisode.mypage.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_forward_ios
import com.boostcamp.mapisode.designsystem.R.drawable.ic_document
import com.boostcamp.mapisode.designsystem.R.drawable.ic_edit
import com.boostcamp.mapisode.designsystem.R.drawable.ic_withdrawal
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeDivider
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.Thickness
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.mypage.R

@Composable
internal fun MypageRoute() {
	MypageScreen(
		name = "이름",
		profileUrl = "https://avatars.githubusercontent.com/u/77449569?v=4",
	)
}

@Composable
private fun MypageScreen(
	name: String,
	profileUrl: String,
	modifier: Modifier = Modifier,
) {
	MapisodeScaffold(
		modifier = modifier.fillMaxSize(),
		isNavigationBarPaddingExist = true,
		isStatusBarPaddingExist = true,
		topBar = {
			Column(modifier = Modifier.fillMaxWidth()) {
				TopAppBar(
					title = stringResource(R.string.mypage_title),
				) {
					MapisodeText(
						text = stringResource(R.string.mypage_logout),
						modifier = Modifier.clickable { },
						style = MapisodeTheme.typography.labelLarge,
					)
				}

				Spacer(modifier = Modifier.height(8.dp))

				MapisodeDivider(thickness = Thickness.Thin)
			}
		},
	) { innerPadding ->
		Box(
			modifier = modifier
				.padding(innerPadding)
				.fillMaxSize(),
			contentAlignment = Alignment.TopCenter,
		) {
			Column(
				modifier = modifier
					.wrapContentHeight()
					.fillMaxWidth(0.9f),
			) {
				Spacer(modifier = Modifier.height(16.dp))

				Profile(
					name = name,
					profileUrl = profileUrl,
				)

				Spacer(modifier = Modifier.height(16.dp))

				MapisodeDivider(thickness = Thickness.Thin)

				Spacer(modifier = Modifier.height(16.dp))

				MapisodeText(
					text = stringResource(R.string.mypage_account),
					style = MapisodeTheme.typography.labelLarge,
				)

				Spacer(modifier = Modifier.height(16.dp))

				MyPageContent(
					text = stringResource(R.string.mypage_terms_of_use),
					iconId = ic_document,
					onClick = { },
				)

				Spacer(modifier = Modifier.height(20.dp))

				MyPageContent(
					text = stringResource(R.string.mypage_withdrawal),
					iconId = ic_withdrawal,
					onClick = { },
				)
			}
		}
	}
}

@Composable
private fun Profile(
	name: String,
	profileUrl: String,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 4.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		AsyncImage(
			model = profileUrl,
			contentDescription = stringResource(R.string.mypage_profile_image),
			modifier = Modifier
				.size(48.dp)
				.clip(CircleShape),
		)

		Spacer(modifier = Modifier.width(16.dp))

		MapisodeText(
			text = name,
			style = MapisodeTheme.typography.bodyLarge,
		)

		Spacer(modifier = Modifier.weight(1f))

		MapisodeIcon(
			id = ic_edit,
			iconSize = IconSize.Large,
		)
	}
}

@Composable
private fun MyPageContent(
	text: String,
	iconId: Int,
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
) {
	Column(modifier = modifier.wrapContentSize()) {
		Row(
			modifier = modifier
				.fillMaxWidth()
				.clickable { onClick() }
				.padding(vertical = 4.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			MapisodeIcon(
				id = iconId,
				iconSize = IconSize.Large,
			)

			Spacer(modifier = Modifier.width(8.dp))

			MapisodeText(
				text = text,
				style = MapisodeTheme.typography.bodyMedium,
			)

			Spacer(modifier = Modifier.weight(1f))

			MapisodeIcon(id = ic_arrow_forward_ios)
		}
		Spacer(modifier = Modifier.height(16.dp))
		MapisodeDivider(thickness = Thickness.Thin)
	}
}
