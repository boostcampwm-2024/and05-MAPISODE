package com.boostcamp.mapisode.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.R.drawable
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeIconButton
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

@Composable
fun LoginScreen(
	modifier: Modifier = Modifier,
	googleSignInClicked: () -> Unit,
) {
	Column(
		modifier = modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Spacer(modifier = Modifier.weight(3f))

		MapisodeIcon(
			id = drawable.ic_mapisode_brand_text,
			modifier = Modifier
				.fillMaxWidth(0.45f)
				.aspectRatio(2.8f),
			tint = null,
		)

		Spacer(modifier = Modifier.weight(1f))

		Image(
			painter = painterResource(id = drawable.ic_mapisode_sublogo_foreground),
			contentDescription = stringResource(R.string.login_mapisode_subicon),
			modifier = Modifier
				.fillMaxWidth()
				.aspectRatio(1.5f),
			contentScale = ContentScale.Fit,
		)

		Spacer(modifier = Modifier.weight(1f))

		MapisodeText(
			text = stringResource(R.string.login_app_short_description),
			style = MapisodeTheme.typography.headlineSmall,
		)

		Spacer(modifier = Modifier.height(15.dp))

		MapisodeText(
			text = stringResource(R.string.login_app_long_description),
			modifier = Modifier.align(Alignment.CenterHorizontally),
			style = MapisodeTheme.typography.bodyLarge.copy(
				textAlign = TextAlign.Center,
			),
		)

		Spacer(modifier = Modifier.weight(1f))

		MapisodeIconButton(
			onClick = googleSignInClicked,
			modifier = Modifier
				.fillMaxWidth(0.8f)
				.weight(1f),
		) {
			Row(
				modifier = Modifier.fillMaxSize(),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.Center,
			) {
				Image(
					painter = painterResource(id = drawable.ic_google),
					contentDescription = "구글 로그인",
					modifier = Modifier
						.fillMaxHeight(0.5f)
						.aspectRatio(1f),
					contentScale = ContentScale.Fit,
				)

				Spacer(modifier = Modifier.width(24.dp))

				MapisodeText(
					text = "구글 계정으로 로그인",
					style = MapisodeTheme.typography.bodyLarge,
				)
			}
		}

		Spacer(modifier = Modifier.weight(2f))
	}
}

@Preview(
	showBackground = true,
	showSystemUi = true,
)
@Composable
fun LoginScreenPreview() {
	LoginScreen(
		googleSignInClicked = {},
	)
}
