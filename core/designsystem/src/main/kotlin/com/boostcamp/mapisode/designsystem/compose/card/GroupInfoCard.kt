package com.boostcamp.mapisode.designsystem.compose.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.boostcamp.mapisode.designsystem.R
import com.boostcamp.mapisode.designsystem.compose.MapisodeDivider
import com.boostcamp.mapisode.designsystem.compose.MapisodeText
import com.boostcamp.mapisode.designsystem.compose.Thickness
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import com.boostcamp.mapisode.model.GroupModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun GroupInfoCard(
	group: GroupModel,
	modifier: Modifier = Modifier,
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(10.dp)
			.height(140.dp),
	) {
		AsyncImage(
			model = group.imageUrl,
			contentDescription = null,
			modifier = Modifier
				.size(140.dp)
				.clip(shape = RoundedCornerShape(16.dp)),
			contentScale = ContentScale.Crop,
		)
		MapisodeDivider(thickness = Thickness.Thick)
		Column(
			modifier = Modifier
				.fillMaxHeight()
				.wrapContentWidth(),
			verticalArrangement = Arrangement.Center,
		) {
			MapisodeText(
				text = group.name,
				style = MapisodeTheme.typography.titleMedium
					.copy(fontWeight = FontWeight.SemiBold),
				maxLines = 1,
			)

			Spacer(modifier = Modifier.padding(4.dp))

			MapisodeText(
				text = stringResource(R.string.group_created_date) +
					SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
						.format(group.createdAt),
				style = MapisodeTheme.typography.labelMedium,
				maxLines = 1,
			)

			Spacer(modifier = Modifier.height(4.dp))

			MapisodeText(
				text = stringResource(
					R.string.group_user_count,
					stringResource(R.string.group_members_number),
					group.members.size,
					stringResource(R.string.group_member_count),
				),
				style = MapisodeTheme.typography.labelMedium,
			)

			Spacer(modifier = Modifier.height(4.dp))

			MapisodeText(
				text = stringResource(R.string.group_recent_upload),
				style = MapisodeTheme.typography.labelMedium,
			)
		}
	}
}
