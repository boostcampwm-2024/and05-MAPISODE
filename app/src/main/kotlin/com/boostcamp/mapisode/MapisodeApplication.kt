package com.boostcamp.mapisode

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.initialize
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MapisodeApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		initTimber()
		Firebase.initialize(context = this)
		Firebase.appCheck.installAppCheckProviderFactory(
			PlayIntegrityAppCheckProviderFactory.getInstance(),
		)
	}

	private fun initTimber() {
		if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
	}
}
