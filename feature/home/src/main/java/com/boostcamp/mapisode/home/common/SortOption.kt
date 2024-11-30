package com.boostcamp.mapisode.home.common

import com.boostcamp.mapisode.home.R as S

enum class SortOption(val label: Int) {
	LATEST(S.string.sort_option_recent),
	OLDEST(S.string.sort_option_oldest),
	NAME(S.string.sort_option_name),
}
