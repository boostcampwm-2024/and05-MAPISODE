package com.boostcamp.mapisode.home.edit

import androidx.compose.runtime.Immutable
import com.boostcamp.mapisode.model.EpisodeModel
import com.boostcamp.mapisode.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class EpisodeEditState(
	val isInitializing: Boolean = true,
	val isSelectingLocation: Boolean = false,
	val isSelectingPicture: Boolean = false,
	val isEpisodeEditError: Boolean = false,
	val episode: EpisodeModel? = null,
	val groups: PersistentList<GroupsId> = persistentListOf(),
	val editedEpisode: EpisodeModel? = null,
) : UiState

data class GroupsId(val id: String, val name: String)
