package com.boostcamp.mapisode.mypage.screen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.auth.GoogleOauth
import com.boostcamp.mapisode.designsystem.R.drawable.ic_arrow_forward_ios
import com.boostcamp.mapisode.designsystem.R.drawable.ic_document
import com.boostcamp.mapisode.designsystem.R.drawable.ic_edit
import com.boostcamp.mapisode.designsystem.compose.IconSize
import com.boostcamp.mapisode.designsystem.compose.MapisodeDialog
import com.boostcamp.mapisode.designsystem.compose.MapisodeDivider
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.Thickness
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.mypage.BuildConfig
import com.boostcamp.mapisode.mypage.R
import com.boostcamp.mapisode.mypage.intent.MypageIntent
import com.boostcamp.mapisode.mypage.sideeffect.MypageSideEffect
import com.boostcamp.mapisode.mypage.viewmodel.MypageViewModel

@Composable
internal fun MypageRoute(
	onProfileEditClick: () -> Unit,
	onNavigateToLogin: () -> Unit,
	viewModel: MypageViewModel = hiltViewModel(),
) {
	val context = LocalContext.current
	val googleOauth = GoogleOauth(context)
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()

	LaunchedEffect(Unit) {
		viewModel.onIntent(MypageIntent.Init)
	}

	LaunchedEffect(Unit) {
		viewModel.sideEffect.collect { sideEffect ->
			when (sideEffect) {
				is MypageSideEffect.Idle -> {}
				is MypageSideEffect.NavigateToLoginScreen -> onNavigateToLogin()
				is MypageSideEffect.NavigateToEditScreen -> onProfileEditClick()
				is MypageSideEffect.ShowToast -> {
					Toast.makeText(
						context,
						sideEffect.messageResId,
						Toast.LENGTH_SHORT,
					).show()
				}

				is MypageSideEffect.OpenPrivacyPolicy -> {
					val url = BuildConfig.PRIVACY_POLICY
					try {
						val customTabsIntent = CustomTabsIntent.Builder()
							.setShowTitle(true)
							.build()
						customTabsIntent.launchUrl(context, Uri.parse(url))
					} catch (e: Exception) {
						val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
						context.startActivity(intent)
					}
				}
			}
		}
	}

	if (uiState.showWithdrawalDialog) {
		MapisodeDialog(
			titleText = stringResource(R.string.mypage_withdrawal_title),
			contentText = stringResource(R.string.mypage_withdrawal_message),
			confirmText = stringResource(R.string.mypage_withdrawal_confirm),
			cancelText = stringResource(R.string.mypage_withdrawal_cancel),
			onResultRequest = { isConfirm ->
				if (isConfirm) viewModel.onIntent(MypageIntent.ConfirmClick(googleOauth))
			},
			onDismissRequest = {
				viewModel.onIntent(MypageIntent.TurnOffDialog)
			},
		)
	}

	MypageScreen(
		name = uiState.name,
		profileUrl = uiState.profileUrl,
		onLogoutClick = { viewModel.onIntent(MypageIntent.LogoutClick(googleOauth)) },
		onProfileEditClick = onProfileEditClick,
		onPrivacyPolicyClick = {
			viewModel.onIntent(
				MypageIntent.PrivacyPolicyClick(
					context,
					true,
				),
			)
		},
		onWithdrawalClick = { viewModel.onIntent(MypageIntent.WithdrawalClick) },
	)
}

@Composable
private fun MypageScreen(
	name: String,
	profileUrl: String,
	onLogoutClick: () -> Unit,
	onProfileEditClick: () -> Unit,
	onPrivacyPolicyClick: () -> Unit,
	onWithdrawalClick: () -> Unit,
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
						modifier = Modifier.clickable { onLogoutClick() },
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
					onProfileEditClick = onProfileEditClick,
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
					onClick = onPrivacyPolicyClick,
				)

				Spacer(modifier = Modifier.height(20.dp))
			}
		}
	}
}

@Composable
private fun Profile(
	name: String,
	profileUrl: String,
	onProfileEditClick: () -> Unit,
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
				.size(52.dp)
				.clip(CircleShape),
			contentScale = ContentScale.Crop,
		)

		Spacer(modifier = Modifier.width(16.dp))

		MapisodeText(
			text = name,
			style = MapisodeTheme.typography.titleLarge,
		)

		Spacer(modifier = Modifier.weight(1f))

		MapisodeIconButton(
			onClick = onProfileEditClick,
		) {
			MapisodeIcon(
				id = ic_edit,
				iconSize = IconSize.Large,
			)
		}
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
