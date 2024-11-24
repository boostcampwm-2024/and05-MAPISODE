package com.boostcamp.mapisode.mygroup.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.Direction
import com.boostcamp.mapisode.designsystem.compose.MapisodeDivider
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.MapisodeTextField
import com.boostcamp.mapisode.designsystem.compose.TextAlignment
import com.boostcamp.mapisode.designsystem.compose.Thickness
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.mygroup.R as S

@Composable
fun GroupJoinScreen(
	onBackClick: () -> Unit,
) {
	val focusManager = LocalFocusManager.current
	var codeText by remember { mutableStateOf("") }
	var isInput by remember { mutableStateOf(false) }
	var isValid by remember { mutableStateOf(false) }

	MapisodeScaffold(
		modifier = Modifier.pointerInput(Unit) {
			detectTapGestures(
				onPress = {
					focusManager.clearFocus()
				},
			)
		},
		isNavigationBarPaddingExist = true,
		isStatusBarPaddingExist = true,
		topBar = {
			TopAppBar(
				title = "그룹 참여",
				navigationIcon = {
					MapisodeIconButton(
						onClick = onBackClick,
					) {
						MapisodeIcon(
							id = R.drawable.ic_arrow_back_ios,
							contentDescription = "Back",
						)
					}
				},
			)
		},
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
				.padding(horizontal = 20.dp),
		) {
			LazyColumn(
				modifier = Modifier
					.fillMaxSize(),
				horizontalAlignment = Alignment.CenterHorizontally,
			) {
				item {
					Column(
						modifier = Modifier.padding(vertical = 8.dp),
						horizontalAlignment = Alignment.CenterHorizontally,
					) {
						MapisodeText(
							text = stringResource(S.string.group_join_sub_title),
							style = MapisodeTheme.typography.titleLarge,
						)
						Spacer(modifier = Modifier.padding(8.dp))
						MapisodeText(
							modifier = Modifier.fillParentMaxWidth(),
							textAlignment = TextAlignment.Center,
							text = stringResource(S.string.group_join_direction),
						)
					}
				}
				item {
					Column(
						modifier = Modifier.padding(vertical = 20.dp),
					) {
						MapisodeText(
							modifier = Modifier
								.fillParentMaxWidth()
								.padding(start = 4.dp),
							text = stringResource(S.string.group_join_textinput_label),
							style = MapisodeTheme.typography.labelLarge,
						)
						Spacer(modifier = Modifier.padding(4.dp))
						MapisodeTextField(
							modifier = Modifier.fillParentMaxWidth(),
							value = codeText,
							onValueChange = { text -> codeText = text },
							placeholder = stringResource(S.string.group_join_textfield_hint),
						)
					}
				}
				item {
					MapisodeFilledButton(
						modifier = Modifier
							.fillParentMaxWidth()
							.height(40.dp),
						onClick = {
							isInput = true
							if (codeText.length < 2) {
								isValid = false
							} else {
								isValid = true
							}
							// TODO : 서버에 코드 요청
						},
						text = stringResource(S.string.group_join_btn_direction),
						enabled = codeText.isNotBlank(),
						showRipple = true,
					)
					Spacer(modifier = Modifier.padding(10.dp))
					MapisodeDivider(direction = Direction.Horizontal, thickness = Thickness.Thin)
					Spacer(modifier = Modifier.padding(10.dp))
				}
				if (isValid && isInput) {
					item {
						ConfirmJoinGroup()
						Spacer(modifier = Modifier.padding(bottom = 70.dp))
					}
				}
				if (!isValid && isInput) {
					item {
						MapisodeText(
							text = "존재하지 않는 그룹입니다.",
							style = MapisodeTheme.typography.bodyLarge,
						)
					}
				}
			}
			if (isValid && isInput) {
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 30.dp)
						.align(Alignment.BottomCenter)
						.background(MapisodeTheme.colorScheme.surfaceBackground),
				) {
					Spacer(modifier = Modifier.padding(top = 4.dp))
					MapisodeDivider(direction = Direction.Horizontal, thickness = Thickness.Thin)
					Spacer(modifier = Modifier.padding(5.dp))
					MapisodeFilledButton(
						onClick = {},
						text = "참여하기",
						showRipple = true,
					)
				}
			}
		}
	}
}

@Composable
fun ConfirmJoinGroup(
) {
	Column(
		modifier = Modifier
			.padding(horizontal = 30.dp)
			.fillMaxHeight(),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		MapisodeText(
			text = stringResource(S.string.group_overview),
			modifier = Modifier.fillMaxWidth(),
			style = MapisodeTheme.typography.labelLarge,
		)
		Spacer(modifier = Modifier.padding(4.dp))
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.border(
					width = 1.dp,
					color = MapisodeTheme.colorScheme.menuStroke,
					shape = RoundedCornerShape(12.dp),
				)
				.padding(10.dp)
				.height(140.dp),
		) {
			AsyncImage(
				model = "https://avatars.githubusercontent.com/u/127717111?v=4",
				contentDescription = "",
				modifier = Modifier
					.size(140.dp)
					.clip(shape = RoundedCornerShape(16.dp)),
			)
			MapisodeDivider(thickness = Thickness.Thick)
			Column(
				modifier = Modifier
					.fillMaxHeight()
					.wrapContentWidth(),
				verticalArrangement = Arrangement.Center,
			) {
				MapisodeText(
					text = "McDonald Trumpppp",
					style = MapisodeTheme.typography.titleMedium
						.copy(fontWeight = FontWeight.SemiBold),
					maxLines = 1,
				)
				Spacer(modifier = Modifier.padding(4.dp))

				MapisodeText(
					text = stringResource(S.string.group_created_date),
					style = MapisodeTheme.typography.labelMedium,
				)
				MapisodeText(
					text = stringResource(S.string.group_user_number),
					style = MapisodeTheme.typography.labelMedium,
				)
				MapisodeText(
					text = stringResource(S.string.group_recent_upload),
					style = MapisodeTheme.typography.labelMedium,
				)
			}
		}
		Spacer(modifier = Modifier.padding(10.dp))
		MapisodeText(
			modifier = Modifier
				.fillMaxWidth()
				.padding(start = 4.dp),
			text = stringResource(S.string.group_descript),
			style = MapisodeTheme.typography.labelLarge,
		)
		Spacer(modifier = Modifier.padding(4.dp))
		Box(
			modifier = Modifier
				.border(
					width = 1.dp,
					color = MapisodeTheme.colorScheme.menuStroke,
					shape = RoundedCornerShape(8.dp),
				)
				.padding(10.dp)
				.wrapContentHeight()
				.heightIn(min = 100.dp),
		) {
			MapisodeText(
				modifier = Modifier.fillMaxWidth(),
				text = "테\n스\n트\n테\n스\n트\n테\n\n\n\n\n\n\n\n스트",
				style = MapisodeTheme.typography.labelLarge,
			)
		}
	}
}
