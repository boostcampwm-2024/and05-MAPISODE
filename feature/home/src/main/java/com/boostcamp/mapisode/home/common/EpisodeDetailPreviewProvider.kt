package com.boostcamp.mapisode.home.common

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.boostcamp.mapisode.home.common.HomeConstant.MOCK_IMAGE_URL
import com.boostcamp.mapisode.home.detail.EpisodeDetailState
import com.boostcamp.mapisode.model.EpisodeModel

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
