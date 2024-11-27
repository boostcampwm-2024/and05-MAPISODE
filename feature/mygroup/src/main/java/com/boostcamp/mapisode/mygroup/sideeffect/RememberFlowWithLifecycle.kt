package com.boostcamp.mapisode.mygroup.sideeffect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> rememberFlowWithLifecycle(
	flow: Flow<T>,
	lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
	initialValue: T,
): State<T> = remember(flow, lifecycleOwner) {
	flow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
}.collectAsState(initial = initialValue)
