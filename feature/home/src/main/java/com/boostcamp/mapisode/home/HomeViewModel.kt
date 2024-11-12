package com.boostcamp.mapisode.home

import com.boostcamp.mapisode.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel<HomeState, HomeSideEffect>(HomeState()) {
	fun onIntent(intent: HomeIntent) {
		when (intent) {
			is HomeIntent.Increment -> increment()
			is HomeIntent.Decrement -> decrement()
		}
	}

	private fun increment() {
		intent {
			copy(count = count + 1)
		}
		postSideEffect(HomeSideEffect.ShowToast("Incremented ${currentState.count}"))
	}

	private fun decrement() {
		intent {
			copy(count = count - 1)
		}
		postSideEffect(HomeSideEffect.ShowToast("Decremented ${currentState.count}"))
	}
}
