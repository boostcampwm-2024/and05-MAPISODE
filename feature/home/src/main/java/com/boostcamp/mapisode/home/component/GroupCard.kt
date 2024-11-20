package com.boostcamp.mapisode.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.AppTypography

@Composable
fun GroupCard(
	groupImage: String,
	groupName: String,
	modifier: Modifier = Modifier,
	onClick: () -> Unit = {},
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.clickable(onClick = onClick),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Box(
			modifier = Modifier
				.size(120.dp)
				.clip(RoundedCornerShape(16.dp)),
			contentAlignment = Alignment.Center,
		) {
			// TODO: PlaceHolder 및 에러 이미지 추가 필요
			AsyncImage(
				model = groupImage,
				contentDescription = groupName,
				contentScale = ContentScale.Crop,
				modifier = Modifier.fillMaxSize(),
			)
		}

		Spacer(modifier = Modifier.size(4.dp))

		MapisodeText(
			text = groupName,
			modifier = Modifier.width(120.dp),
			style = AppTypography.bodyMedium,
		)

		// TODO : 업데이트 일자 텍스트 추가 예정
	}
}

@Preview(showBackground = true)
@Composable
fun GroupCardPreview() {
	GroupCard(
		groupImage = "https://github.com/user-attachments/assets/34d47b54-1ba6-48c7-adc6-a1d3f050e131",
		groupName = "그룹 이름",
	)
}
