package com.boostcamp.mapisode.mypage

import com.boostcamp.mapisode.ui.base.UiState

data class MypageState(
	val isLoading: Boolean = true,
	val name: String = "",
	val profileUrl: String = "",
	val showWithdrawalDialog: Boolean = false,
) : UiState

data class ProfileEditState(
	val isLoading: Boolean = true,
	val uid: String = "",
	val name: String = "",
	val profileUrl: String = "",
	val isPhotoPickerClicked: Boolean = false,
) : UiState
