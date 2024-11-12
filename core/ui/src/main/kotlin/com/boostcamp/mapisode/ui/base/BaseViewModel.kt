package com.boostcamp.mapisode.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

abstract class BaseViewModel<UI_STATE : UiState, SIDE_EFFECT : SideEffect>(
	initialState: UI_STATE,
) : ViewModel() {

	private val _uiState = MutableStateFlow(initialState)
	val uiState = _uiState.asStateFlow()

	private val _sideEffect: Channel<SIDE_EFFECT> = Channel(Channel.UNLIMITED)
	val sideEffect = _sideEffect.receiveAsFlow()

	protected val currentState: UI_STATE
		get() = _uiState.value

	protected fun intent(reduce: UI_STATE.() -> UI_STATE) {
		_uiState.update { currentState.reduce() }
	}

	protected fun postSideEffect(vararg effects: SIDE_EFFECT) {
		effects.forEach { effect ->
			val result = _sideEffect.trySend(effect)
			if (result.isFailure) {
				// TODO : 에러 로직처리 방법 결정 후 수정
				Timber.e(result.exceptionOrNull())
			}
		}
	}
}
