package com.boostcamp.mapisode.home.common

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.boostcamp.mapisode.home.common.HomeConstant.MOCK_IMAGE_URL
import com.boostcamp.mapisode.home.detail.EpisodeDetailState
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.model.UserModel
import java.util.Date

class EpisodeDetailPreviewProvider : PreviewParameterProvider<EpisodeDetailState> {
	override val values: Sequence<EpisodeDetailState> = sequenceOf(
		EpisodeDetailState(
			isLoading = false,
			episode = EpisodeModel(
				title = "Episode - 1 Image",
				imageUrls = listOf(MOCK_IMAGE_URL),
			),
		),
		EpisodeDetailState(
			isLoading = false,
			episode = EpisodeModel(
				title = "Episode - 2 Images",
				imageUrls = listOf(
					MOCK_IMAGE_URL,
					MOCK_IMAGE_URL,
				),
			),
		),
		EpisodeDetailState(
			isLoading = false,
			episode = EpisodeModel(
				title = "Episode - 3 Images",
				imageUrls = listOf(
					MOCK_IMAGE_URL,
					MOCK_IMAGE_URL,
					MOCK_IMAGE_URL,
				),
				address = "경기도 성남시 분당구 어쩌구 저쩌구",
				tags = listOf(
					"태그1",
					"태그2",
					"태그3",
					"태그4",
					"태그5",
					"태그6",
					"태그7",
					"태그8",
					"태그9",
					"태그10",
				),
				category = "EAT",
			),
			author = UserModel(
				name = "Author",
				profileUrl = MOCK_IMAGE_URL,
				uid = "author-uid",
				email = "author-email",
				joinedAt = Date(),
				groups = emptyList(),
			),
		),
		EpisodeDetailState(
			isLoading = false,
			episode = EpisodeModel(
				title = "Episode - 4 Images",
				imageUrls = listOf(
					MOCK_IMAGE_URL,
					MOCK_IMAGE_URL,
					MOCK_IMAGE_URL,
					MOCK_IMAGE_URL,
				),
			),
		),
	)
}
