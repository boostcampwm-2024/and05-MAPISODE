package com.boostcamp.mapisode.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.R.drawable
import com.boostcamp.mapisode.designsystem.compose.LocalTextStyle
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeScaffold
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeFilledButton
import com.boostcamp.mapisode.designsystem.compose.button.MapisodeOutlinedButton
import com.boostcamp.mapisode.designsystem.compose.topbar.TopAppBar

@Composable
fun SignUpScreen(
	modifier: Modifier = Modifier,
) {
	MapisodeScaffold(
		modifier = modifier.fillMaxSize(),
		isStatusBarPaddingExist = true,
		topBar = { SignUpTopBar() },
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
				.padding(horizontal = 20.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Spacer(modifier = Modifier.height(16.dp))

			MapisodeText(
				text = stringResource(R.string.login_nickname),
				modifier = Modifier.align(Alignment.Start),
				style = LocalTextStyle.current.copy(
					fontSize = 18.dp,
					fontWeight = FontWeight.SemiBold,
				),
			)

			Spacer(modifier = Modifier.height(10.dp))

			MapisodeText("Placeholder for the textField")

			Spacer(modifier = Modifier.height(50.dp))

			MapisodeText(
				text = stringResource(R.string.login_profile_image),
				modifier = Modifier.align(Alignment.Start),
				style = LocalTextStyle.current.copy(
					fontSize = 18.dp,
					fontWeight = FontWeight.SemiBold,
				),
			)

			Spacer(modifier = Modifier.height(10.dp))

			MapisodeOutlinedButton(
				text = "placeholder for 이미지를 업로드해주세요",
				onClick = { },
				modifier = Modifier
					.fillMaxWidth()
					.height(320.dp),
			)

			Spacer(modifier = Modifier.weight(1f))

			MapisodeFilledButton(
				text = stringResource(R.string.login_next),
				onClick = { },
				modifier = Modifier
					.fillMaxWidth(),
			)

			Spacer(modifier = Modifier.height(30.dp))
		}
	}
}

@Composable
fun SignUpTopBar(
) {
	TopAppBar(
		navigationIcon = {
			MapisodeIcon(
				id = drawable.ic_arrow_back_ios,
				modifier = Modifier
					.clickable { },
			)
		},
		title = stringResource(R.string.login_signup),
	)
}

@Preview(
	showBackground = true,
	showSystemUi = true,
	widthDp = 360,
	heightDp = 780,
)
@Composable
fun SignUpScreenPreview() {
	SignUpScreen()
}
