package com.boostcamp.mapisode

import android.app.Application
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MapisodeApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		initTimber()
	}

	private fun initTimber() {
		if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
	}
}
