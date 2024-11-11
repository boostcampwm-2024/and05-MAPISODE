package com.boostcamp.mapisode.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			val navigator: MainNavigator = rememberMainNavigator()

			MapisodeTheme {
				MainScreen(navigator = navigator)
			}
		}
	}
}
