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
				content = "파이브가이즈 다녀왔는데 정말 만족스러웠어! \uD83C\uDF54 매장에 들어서자마자 미국 느낌이 확 나서 신기했고, 오픈 주방에서 직접 버거 굽고 준비하는 모습 보니까 신선하고 믿음이 가더라구. 주문할 때 토핑을 내 맘대로 추가할 수 있어서 완전 내 스타일로 커스텀할 수 있었어. 버거는 손에 딱 잡히는 묵직한 느낌이 좋았고, 한 입 베어 물자마자 육즙이 팡 터지는데 진짜 맛있었어! 감자튀김도 양이 푸짐하고 바삭해서 버거랑 환상 조합이더라. \uD83C\uDF5F",
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
