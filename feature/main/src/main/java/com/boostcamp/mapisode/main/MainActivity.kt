package com.boostcamp.mapisode.main

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	var isReady by mutableStateOf(false)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		enableEdgeToEdge()

		setContent {
			val navigator: MainNavigator = rememberMainNavigator()
			MapisodeTheme {
				MainScreen(navigator = navigator)
			}
			if (navigator.endSplash) {
				LaunchedEffect(Unit) {
					delay(300)
					isReady = true
				}
			}
		}

		val content: View = findViewById(android.R.id.content)
		content.viewTreeObserver.addOnPreDrawListener(
			object : ViewTreeObserver.OnPreDrawListener {
				override fun onPreDraw(): Boolean = if (isReady) {
					content.viewTreeObserver.removeOnPreDrawListener(this)
					true
				} else {
					false
				}
			},
		)
	}
}
