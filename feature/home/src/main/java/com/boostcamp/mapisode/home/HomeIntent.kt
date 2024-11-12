package com.boostcamp.mapisode.home

sealed class HomeIntent {
	data object Increment : HomeIntent()
	data object Decrement : HomeIntent()
}
