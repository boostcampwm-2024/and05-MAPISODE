package com.boostcamp.mapisode.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.boostcamp.mapisode.designsystem.compose.BottomBarController
import com.boostcamp.mapisode.designsystem.compose.LocalMapisodeShowBotBar
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		enableEdgeToEdge()

		setContent {
			val navigator: MainNavigator = rememberMainNavigator()

			CompositionLocalProvider(LocalMapisodeShowBotBar provides BottomBarController) {
				MapisodeTheme {
					MainScreen(navigator = navigator)
				}
			}
		}
	}
}
