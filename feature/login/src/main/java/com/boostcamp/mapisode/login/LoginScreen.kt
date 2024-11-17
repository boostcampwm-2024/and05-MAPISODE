package com.boostcamp.mapisode.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.boostcamp.mapisode.designsystem.R.drawable
import com.boostcamp.mapisode.designsystem.compose.LocalTextStyle
import com.boostcamp.mapisode.designsystem.compose.MapisodeIcon
import com.boostcamp.mapisode.designsystem.compose.MapisodeText

@Composable
fun LoginScreen(
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Spacer(modifier = Modifier.height(115.dp))

		MapisodeIcon(
			id = drawable.ic_mapisode_brand_text,
			modifier = Modifier
				.height(60.dp)
				.width(170.dp),
			tint = null,
		)

		Spacer(modifier = Modifier.height(85.dp))

		Image(
			painter = painterResource(id = drawable.ic_mapisode_sublogo),
			contentDescription = stringResource(R.string.login_mapisode_subicon),
			modifier = Modifier
				.height(170.dp)
				.width(250.dp),
			contentScale = ContentScale.Fit,
		)

		Spacer(modifier = Modifier.height(90.dp))

		MapisodeText(
			stringResource(R.string.login_app_short_description),
			style = LocalTextStyle.current.copy(
				fontSize = 20.dp,
				fontWeight = FontWeight.Bold,
			),
		)

		Spacer(modifier = Modifier.height(10.dp))

		MapisodeText(
			text = stringResource(R.string.login_app_long_description),
			style = LocalTextStyle.current.copy(
				fontSize = 16.dp,
				textAlign = TextAlign.Center,
			),
		)

		Spacer(modifier = Modifier.height(45.dp))

		MapisodeText("Placeholder for Login Button")
	}
}

@Preview(
	showBackground = true,
	showSystemUi = true,
)
@Composable
fun LoginScreenPreview() {
	LoginScreen()
}
