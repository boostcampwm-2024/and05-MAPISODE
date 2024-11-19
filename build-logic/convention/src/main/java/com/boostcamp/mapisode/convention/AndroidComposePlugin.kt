package com.boostcamp.mapisode.convention

import com.android.build.gradle.LibraryExtension
import com.boostcamp.mapisode.convention.extension.configureComposeAndroid
import com.boostcamp.mapisode.convention.extension.getLibrary
import com.boostcamp.mapisode.convention.extension.implementation
import com.boostcamp.mapisode.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

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

			dependencies {
				implementation(libs.getLibrary("kotlinx.immutable"))
			}
		}
	}
}
