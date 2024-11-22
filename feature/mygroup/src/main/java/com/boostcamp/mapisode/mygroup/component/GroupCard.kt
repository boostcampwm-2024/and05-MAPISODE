package com.boostcamp.mapisode.mygroup.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.ripple.MapisodeRippleAIndication
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme

data class ItemData(
	val imageUrl: String,
	val title: String,
	val content: String,
)

@Composable
fun GroupCard(
	onGroupDetailClick: () -> Unit,
	imageUrl: String,
	title: String,
	content: String,
) {
	Column(
		modifier = Modifier
			.padding(vertical = 10.dp)
			.wrapContentSize()
			.clip(RoundedCornerShape(16.dp))
			.clickable(
				interactionSource = null,
				indication = MapisodeRippleAIndication,
				onClick = onGroupDetailClick,
			),
	) {
		AsyncImage(
			model = imageUrl,
			contentDescription = title,
			modifier = Modifier
				.size(140.dp)
				.clip(RoundedCornerShape(16.dp)),
			contentScale = ContentScale.Crop,
		)
		Spacer(modifier = Modifier.height(4.dp))
		Column(
			modifier = Modifier
				.padding(start = 4.dp),
			horizontalAlignment = Alignment.Start,
		) {
			MapisodeText(
				text = title,
				style = MapisodeTheme.typography.labelLarge,
			)
			MapisodeText(
				text = content,
				style = MapisodeTheme.typography.labelMedium,
			)
		}
	}
}
