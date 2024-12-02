package com.boostcamp.mapisode.mypage.state

import com.boostcamp.mapisode.ui.base.UiState

data class MypageState(
	val isLoading: Boolean = true,
	val name: String = "",
	val profileUrl: String = "",
	val showWithdrawalDialog: Boolean = false,
) : UiState
