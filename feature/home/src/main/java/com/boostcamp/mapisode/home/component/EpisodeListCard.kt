package com.boostcamp.mapisode.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.common.util.toFormattedString
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import okhttp3.internal.immutableListOf
import java.util.Date
import com.boostcamp.mapisode.home.R as S

@Composable
fun EpisodeListCard(
	imageUrl: String,
	title: String,
	createdBy: String,
	address: String,
	createdAt: Date,
	content: String,
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(130.dp)
			.background(Color.White, shape = RoundedCornerShape(8.dp))
			.border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
			.padding(10.dp),
	) {
		AsyncImage(
			model = imageUrl,
			contentDescription = "Episode Image",
			modifier = Modifier
				.size(110.dp)
				.clip(RoundedCornerShape(8.dp)),
			contentScale = ContentScale.Crop,
		)

		Spacer(modifier = Modifier.width(10.dp))

		Column(
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
		) {
			MapisodeText(
				text = title,
				style = MapisodeTheme.typography.titleMedium
					.copy(fontWeight = FontWeight.SemiBold),
				maxLines = 1,
			)

			Spacer(modifier = Modifier.height(4.dp))

			val textList = immutableListOf(
				stringResource(S.string.overview_created_by) + createdBy,
				stringResource(S.string.overview_location) + address,
				stringResource(S.string.overview_date) + createdAt.toFormattedString(),
				stringResource(S.string.overview_content) + content,
			)

			textList.forEach { text ->
				MapisodeText(
					text = text,
					style = MapisodeTheme.typography.labelMedium,
					maxLines = 1,
				)
			}
		}
	}
}
