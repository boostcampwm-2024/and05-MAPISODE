package com.boostcamp.mapisode.convention

import com.android.build.gradle.LibraryExtension
import com.boostcamp.mapisode.convention.extension.configureComposeAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidComposePlugin : Plugin<Project> {
	override fun apply(target: Project) {
		with(target) {
			pluginManager.apply {
				apply("mapisode.android.library")
				apply("org.jetbrains.kotlin.plugin.compose")
			}

			extensions.configure<LibraryExtension> {
				configureComposeAndroid(this)
			}
		}
	}
}
