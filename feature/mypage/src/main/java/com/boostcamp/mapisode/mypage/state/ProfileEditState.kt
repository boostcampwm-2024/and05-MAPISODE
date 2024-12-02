package com.boostcamp.mapisode.mypage.state

import com.boostcamp.mapisode.ui.base.UiState

data class ProfileEditState(
	val isLoading: Boolean = true,
	val uid: String = "",
	val name: String = "",
	val profileUrl: String = "",
	val isPhotoPickerClicked: Boolean = false,
) : UiState
