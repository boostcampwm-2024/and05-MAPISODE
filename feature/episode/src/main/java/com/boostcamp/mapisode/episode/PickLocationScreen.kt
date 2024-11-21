package com.boostcamp.mapisode.episode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap

@OptIn(ExperimentalNaverMapApi::class)
@Composable
internal fun PickLocationScreen(
	markerState: MarkerState,
	navController: NavController,
) {
	MapisodeScaffold(
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = stringResource(R.string.new_episode_menu_title),
				navigationIcon = {
					MapisodeIconButton(onClick = { navController.navigateUp() }) {
						MapisodeIcon(com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_back_ios)
					}
				},
			)
		},
	) {
		Column {
			NaverMap(
				modifier = Modifier.fillMaxHeight(0.75f),
				uiSettings = MapUiSettings(
					isZoomControlEnabled = false,
					isLocationButtonEnabled = true,
					isLogoClickEnabled = false,
				),
			) {
				Marker(state = markerState)
			}
			Box(
				modifier = Modifier.fillMaxHeight(),
				contentAlignment = Alignment.Center,
			) {
				Column(
					modifier = Modifier.padding(20.dp),
					verticalArrangement = Arrangement.spacedBy(20.dp),
				) {
					MapisodeText(
						text = stringResource(R.string.new_episode_pick_location),
						style = MapisodeTheme.typography.headlineSmall,
					)
					MapisodeText(
						text = stringResource(R.string.new_episode_info_placeholder_location),
						style = MapisodeTheme.typography.bodyLarge,
					)
					MapisodeFilledButton(
						modifier = Modifier.fillMaxWidth(),
						text = stringResource(R.string.new_episode_pick_location_button),
						onClick = { navController.navigate("new_episode_content") },
						showRipple = true,
					)
				}
			}
		}
	}
}
