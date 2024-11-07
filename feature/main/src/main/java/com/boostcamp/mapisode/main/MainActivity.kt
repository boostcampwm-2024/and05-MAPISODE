package com.boostcamp.mapisode.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			val navigator: MainNavigator = rememberMainNavigator()

			MaterialTheme {
				MainScreen(navigator = navigator)
			}
		}
	}
}
