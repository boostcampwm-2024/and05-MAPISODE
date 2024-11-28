package com.boostcamp.mapisode.main

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.boostcamp.mapisode.designsystem.theme.MapisodeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		enableEdgeToEdge()

		var endSplash = false

		setContent {
			val navigator: MainNavigator = rememberMainNavigator()

			MapisodeTheme {
				MainScreen(navigator = navigator)
			}

			LaunchedEffect(Unit) {
				delay(300)
				endSplash = true
			}
		}

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
			val content: View = findViewById(android.R.id.content)
			content.viewTreeObserver.addOnPreDrawListener(
				object : ViewTreeObserver.OnPreDrawListener {
					override fun onPreDraw(): Boolean {
						return if (endSplash) {
							content.viewTreeObserver.removeOnPreDrawListener(this)
							true
						} else {
							false
						}
					}
				},
			)
		}
	}
}
