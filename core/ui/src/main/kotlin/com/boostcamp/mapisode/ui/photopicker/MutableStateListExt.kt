package com.boostcamp.mapisode.ui.photopicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList

@Composable
fun <T : Any> rememberMutableStateListOf(vararg elements: T): SnapshotStateList<T> =
	rememberSaveable(saver = snapshotStateListSaver()) {
		elements.toList().toMutableStateList()
	}

private fun <T : Any> snapshotStateListSaver() = listSaver<SnapshotStateList<T>, T>(
	save = { stateList -> stateList.toList() },
	restore = { it.toMutableStateList() },
)
