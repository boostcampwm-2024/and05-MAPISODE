package com.boostcamp.mapisode

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MapisodeApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		initTimber()
		FirebaseApp.initializeApp(this)
	}

	private fun initTimber() {
		if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
	}
}
